package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple http web server implementation
 * 
 * @author matfures
 *
 */
public class SmartHttpServer {
	/**
	 * Servers address
	 */
	private String address;

	/**
	 * Servers domain name
	 */
	private String domainName;

	/**
	 * Port on which server listens
	 */
	private int port;

	/**
	 * Number of threads working in server
	 */
	private int workerThreads;

	/**
	 * Time after which session expires
	 */
	private int sessionTimeout;

	/**
	 * Map containing supported mimeTypes and corresponding values
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Main thread
	 */
	private ServerThread serverThread;

	/**
	 * Thread pool containing threads that fulfill requests
	 */
	private ExecutorService threadPool;

	/**
	 * Path to document root
	 */
	private Path documentRoot;

	/**
	 * Tracks all workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/**
	 * Registered sessions
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Random number generator for sessions
	 */
	private Random sessionRandom = new Random();

	/**
	 * Constructor. Tries to configure server from given file. If it can't shuts
	 * down.
	 * 
	 * @param configFileName to read from
	 * @throws IllegalArgumentException if given path couldn't be loaded from
	 * @throws NullPointerException     if properties don't contain some needed
	 *                                  value
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch (IOException e) {
			System.out.println("Couldn't load properties from: " + configFileName);
			throw new IllegalArgumentException("Invalid path to config file:" + configFileName);
		}

		address = Objects.requireNonNull(properties.getProperty("server.address"), "server.address not set");
		domainName = Objects.requireNonNull(properties.getProperty("server.domainName"), "server.domainName not set");
		port = Integer.parseInt(Objects.requireNonNull(properties.getProperty("server.port"), "server.port not set"));
		workerThreads = Integer.parseInt(
				Objects.requireNonNull(properties.getProperty("server.workerThreads"), "server.workerThreads not set"));
		sessionTimeout = Integer
				.parseInt(Objects.requireNonNull(properties.getProperty("session.timeout"), "session.timeout not set"));
		documentRoot = Paths.get(
				Objects.requireNonNull(properties.getProperty("server.documentRoot"), "server.documentRoot not set"));

		Path pathToMime = Paths
				.get(Objects.requireNonNull(properties.getProperty("server.mimeConfig"), "server.mimeConfig not set"));
		try {
			Properties mProp = new Properties();
			mProp.load(Files.newInputStream(pathToMime));
			addSupportedTypes(mProp);
		} catch (IOException e) {
			System.out.println("Couldn't load mime properties from: " + pathToMime);
			throw new NullPointerException("Invalid path to config server.mimeConfig file:" + pathToMime);
		}

		Path pathToWorkers = Paths
				.get(Objects.requireNonNull(properties.getProperty("server.workers"), "server.workers not set"));

		List<String> lines;
		try {
			lines = Files.readAllLines(pathToWorkers);
		} catch (Exception e) {
			throw new IllegalArgumentException("Couldn't read from given file:" + pathToWorkers);
		}

		for (String line : lines) {
			if (line.isEmpty() || line.startsWith("#")) {
				continue;
			}

			int indexOfEquals = line.indexOf("=");
			if (indexOfEquals == -1) {
				throw new IllegalArgumentException("Line in invalid format: " + line);
			}

			String fqcn = line.substring(indexOfEquals + 1).trim();
			String path = line.substring(0, indexOfEquals).trim();

			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				if (workersMap.containsKey(path)) {
					throw new IllegalArgumentException("Multiple fqcn given with same key");
				}

				workersMap.put(path, iww);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid fqcn given");
			}
		}

		serverThread = new ServerThread();
	}

	/**
	 * Adds supported types to map
	 * 
	 * @param mProp properties
	 */
	private void addSupportedTypes(Properties mProp) {
		mimeTypes.put("html", Objects.requireNonNull(mProp.getProperty("html"), "html propery wasn't found"));
		mimeTypes.put("htm", Objects.requireNonNull(mProp.getProperty("htm"), "htm propery wasn't found"));
		mimeTypes.put("txt", Objects.requireNonNull(mProp.getProperty("txt"), "txt propery wasn't found"));
		mimeTypes.put("gif", Objects.requireNonNull(mProp.getProperty("gif"), "gif propery wasn't found"));
		mimeTypes.put("png", Objects.requireNonNull(mProp.getProperty("png"), "png propery wasn't found"));
		mimeTypes.put("jpg", Objects.requireNonNull(mProp.getProperty("jpg"), "jpg propery wasn't found"));
	}

	/**
	 * Starts server
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads, x -> {
			Thread thread = new Thread(x);
			thread.setDaemon(true);
			return thread;
		});

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!serverThread.done.get()) {
					synchronized (SmartHttpServer.class) {
						for (var entry : sessions.entrySet()) {
							if (entry.getValue().validUntil < System.currentTimeMillis()) {
								sessions.remove(entry.getKey());
							}
						}
					}

					try {
						Thread.sleep(300 * 1000);
					} catch (InterruptedException ignored) {
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Stops server
	 */
	protected synchronized void stop() {
		if (serverThread.serverSocket != null) {
			try {
				serverThread.serverSocket.close();
			} catch (IOException e) {
			}
		}
		serverThread.done.set(true);
		threadPool.shutdown();
	}

	/**
	 * Main thread
	 * 
	 * @author matfures
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * If true, thread should stop working
		 */
		AtomicBoolean done = new AtomicBoolean(false);

		/**
		 * Socket used
		 */
		ServerSocket serverSocket;

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while (!done.get()) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (Exception e) {
						return;
					}
				}
			} catch (Exception e) {
				throw new IllegalStateException("Errors while runing server thread");
			} finally {
				done.set(true);
				try {
					serverSocket.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * Defines a runnable job of clients desired work
	 * 
	 * @author matfures
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket used
		 */
		private Socket csocket;

		/**
		 * Input stream
		 */
		private PushbackInputStream istream;

		/**
		 * Output stream
		 */
		private OutputStream ostream;

		/**
		 * Version of protocol
		 */
		private String version;

		/**
		 * Method used
		 */
		private String method;

		/**
		 * Host of request
		 */
		private String host;

		/**
		 * Parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/**
		 * Permanent parameters
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();

		/**
		 * List containing cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * Context used
		 */
		private RequestContext context;

		/**
		 * Session id
		 */
		private String SID;

		/**
		 * Constructor
		 * 
		 * @param csocket socket to use
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "File not found");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}

				host = null;
				String sidCandidate = null;
				for (String line : headers) {
					if (line.startsWith("Host:")) {
						host = line.substring(5).trim().split(":")[0];
					}

					if (line.startsWith("Cookie:")) {
						line = line.substring(7);
						String[] arr = line.split(";");
						for (String cookie : arr) {
							String[] pair = cookie.split("=");
							if (pair.length != 2) {
								sendError(ostream, 400, "Bad Request");
								return;
							}

							if (pair[0].trim().equals("sid")) {
								sidCandidate = pair[1].trim();
								sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1);
							}
						}
					}
				}

				if (host == null) {
					host = SmartHttpServer.this.domainName;
				}

				configureSessions(sidCandidate);

				String path = firstLine[1];
				String paramString;
				int indexOfSplit = path.indexOf("?");
				if (indexOfSplit != -1) {
					paramString = path.substring(indexOfSplit + 1);
					path = path.substring(0, indexOfSplit);

					for (String line : paramString.split("&")) {
						String[] arr = line.split("=");
						if (arr.length > 2) {
							sendError(ostream, 400, "Bad request");
							return;
						}

						params.put(arr[0], arr.length == 2 ? arr[1] : "");
					}
				}

				internalDispatchRequest(path.substring(1), true);
			} catch (Exception ignored) {
			} finally {
				try {
					ostream.flush();
					csocket.close();
				} catch (Exception ignored) {
				}
			}
		}

		/**
		 * Configures sessions
		 * 
		 * @param sidCandidate potential session id
		 */
		private void configureSessions(String sidCandidate) {
			synchronized (SmartHttpServer.class) {
				SessionMapEntry sessionEntry = null;
				if (sidCandidate != null) {
					sessionEntry = sessions.get(sidCandidate);
					if (sessionEntry == null) {
						sidCandidate = null;
					} else if (!sessionEntry.host.equals(host)) {
						sidCandidate = null;
					} else if (sessionEntry.validUntil < System.currentTimeMillis()) {
						sessions.remove(sidCandidate);
						sidCandidate = null;
					}
				}

				if (sidCandidate == null) {
					while (true) {
						sidCandidate = randomString(20);
						if (!(sessions.containsKey(sidCandidate))) {
							break;
						}
					}
					RCCookie cookie = new RCCookie("sid", sidCandidate, null, host, "/");
					outputCookies.add(cookie);
					sessionEntry = new SessionMapEntry(sidCandidate, host,
							System.currentTimeMillis() + sessionTimeout * 1000, new ConcurrentHashMap<>());
				} else {
					sessionEntry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
				}

				SID = sessionEntry.sid;
				sessions.put(SID, sessionEntry);
				permPrams = sessionEntry.map;
			}
		}

		/**
		 * Generates random string compond of upper case letters with length given
		 * 
		 * @param length of string
		 * @return pseudo random string
		 */
		private String randomString(int length) {
			char a = Character.valueOf('A');
			StringBuilder sb = new StringBuilder();
			while (length > 0) {
				sb.append((char) (a + sessionRandom.nextInt(26)));
				length--;
			}

			return sb.toString();
		}

		/**
		 * Called for dispatching request
		 * 
		 * @param urlPath    path
		 * @param directCall is call direct
		 * @throws Exception if errors occurred
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (!directCall) {
				urlPath = urlPath.substring(1);
			}
			Path requestedFile = documentRoot.resolve(urlPath);
			if (!requestedFile.normalize().startsWith(documentRoot.normalize())) {
				sendError(ostream, 403, "Forbidden");
				return;
			}

			Path actualUrl = Paths
					.get(requestedFile.normalize().toString().substring(documentRoot.normalize().toString().length()));

			if ((actualUrl.startsWith("/private") || actualUrl.startsWith("/private/")) && directCall) {
				sendError(ostream, 404, "File not found");
				return;
			}

			if (actualUrl.startsWith("/ext/")) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				urlPath = actualUrl.toString().substring(5);
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath;
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				iww.processRequest(context);
				return;
			}

			if (workersMap.containsKey("/" + urlPath.toString())) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}

				workersMap.get("/" + urlPath.toString()).processRequest(context);
				return;
			}

			if (!(Files.exists(requestedFile) && Files.isReadable(requestedFile))) {
				sendError(ostream, 404, "File not found");
				return;
			}

			if (requestedFile.toString().endsWith(".smscr")) {
				SmartScriptParser parser = new SmartScriptParser(
						new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8));
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
				engine.execute();
				return;
			}

			int indexOfDot = requestedFile.toString().lastIndexOf(".");
			String mimeType = null;
			if (indexOfDot != -1) {
				mimeType = mimeTypes.get(requestedFile.toString().substring(indexOfDot + 1));
			}

			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies);
			}
			context.setMimeType(mimeType);
			// rc.setStatusCode(200);
			context.write(Files.readAllBytes(requestedFile));
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

	}

	private static byte[] readRequest(InputStream is) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		l: while (true) {
			int b = is.read();
			if (b == -1)
				return null;
			if (b != 13) {
				bos.write(b);
			}
			switch (state) {
			case 0:
				if (b == 13) {
					state = 1;
				} else if (b == 10)
					state = 4;
				break;
			case 1:
				if (b == 10) {
					state = 2;
				} else
					state = 0;
				break;
			case 2:
				if (b == 13) {
					state = 3;
				} else
					state = 0;
				break;
			case 3:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			case 4:
				if (b == 10) {
					break l;
				} else
					state = 0;
				break;
			}
		}
		return bos.toByteArray();
	}

	/**
	 * Writes error message header
	 * 
	 * @param cos        stream to write on
	 * @param statusCode code
	 * @param statusText message
	 * @throws IOException exception
	 */
	private static void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

		cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
				+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
				+ "\r\n").getBytes(StandardCharsets.US_ASCII));
		cos.flush();

	}

	/**
	 * Extracts header from string
	 * 
	 * @param requestHeader input
	 * @return list of fields
	 */
	private static List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for (String s : requestHeader.split("\n")) {
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == 9 || c == 32) {
				currentLine += s;
			} else {
				if (currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if (!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * Defines an entry for sessions map
	 * 
	 * @author matfures
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Sessions id
		 */
		String sid;

		/**
		 * Sessions host
		 */
		String host;

		/**
		 * Until when is session valid
		 */
		long validUntil;

		/**
		 * Map referencing permParams
		 */
		Map<String, String> map;

		/**
		 * Creates map entry. All arguments mustn't be null
		 * 
		 * @param sid        session id
		 * @param host       host
		 * @param validUntil validity
		 * @param map        should be permParams
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = Objects.requireNonNull(sid);
			this.host = Objects.requireNonNull(host);
			this.validUntil = validUntil;
			this.map = Objects.requireNonNull(map);
		}

	}

	/**
	 * Starts program
	 * 
	 * @param args path to server.properties file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("1 argument is expected");
		} else {
			try {
				var a = new SmartHttpServer(args[0]);
				a.start();
			} catch (Exception e) {
				System.out.println("Error occurred");
			}
		}
	}
}
