package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Defines contex of a web request
 * 
 * @author matfures
 *
 */
public class RequestContext {
	/**
	 * Contexts output stream
	 */
	private OutputStream outputStream;

	/**
	 * Contexts current charset
	 */
	private Charset charset;

	/**
	 * Context encoding page
	 */
	private String encoding = "UTF-8";

	/**
	 * Contexts status code
	 */
	private int statusCode = 200;

	/**
	 * Context status text
	 */
	private String statusText = "OK";

	/**
	 * Contexts mime type
	 */
	private String mimeType = "text/html";

	/**
	 * Contexts content length
	 */
	private Long contentLength = null;

	/**
	 * Collection holding parameters
	 */
	private Map<String, String> parameters;

	/**
	 * Collection holding temporary parameters
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();

	/**
	 * Collection holding persistent parameters
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Collection holding output cookies
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Flag containing information if header has been generated
	 */
	private boolean headerGenerated = false;

	/**
	 * Dispatcher used
	 */
	private IDispatcher dispatcher;

	/**
	 * Current sessions id
	 */
	private String sessionId = null;

	/**
	 * Constructor
	 * 
	 * @param outputStream         to be written on
	 * @param parameters           map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies        list of cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this.outputStream = Objects.requireNonNull(outputStream, "Output stream can't be null");
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * Constructor with extra values
	 * 
	 * @param outputStream         to be written on
	 * @param parameters           map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies        list of cookies
	 * @param temporaryParameters  map of temporary parameters
	 * @param dispatcher           dispatcher used
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Constructor with extra values
	 * 
	 * @param outputStream         to be written on
	 * @param parameters           map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies        list of cookies
	 * @param temporaryParameters  map of temporary parameters
	 * @param dispatcher           dispatcher used
	 * @param sessionId            sessions id
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sessionId) {
		this(outputStream, parameters, persistentParameters, outputCookies, temporaryParameters, dispatcher);
		this.sessionId = sessionId;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists)
	 * 
	 * @param name key of parameter
	 * @return value or null
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Return set of keys contained in parameters. Unmodifiable
	 * 
	 * @return set of keys
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistent parameters map (or null if no
	 * association exists)
	 * 
	 * @param name key of parameter
	 * @return value or null
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Return set of keys contained in persistent parameters. Unmodifiable
	 * 
	 * @return set of keys
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Puts entry into persistent parameters map
	 * 
	 * @param name  key
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes value with associated key from persistent parameters map
	 * 
	 * @param name key of value to be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists)
	 * 
	 * @param name key for value
	 * @return value or null
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Return set of keys contained in temporary parameters. Unmodifiable
	 * 
	 * @return set of keys
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Returns current session id
	 * 
	 * @return session id
	 */
	public String getSessionID() {
		return sessionId;
	}

	/**
	 * Method that stores a value to temporaryParameters map:
	 * 
	 * @param name  of key
	 * @param value value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map:
	 * 
	 * @param name of key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes to output stream
	 * 
	 * @param data to be written
	 * @return this
	 * @throws IOException if errors occurred
	 */
	public RequestContext write(byte[] data) throws IOException {
		generatedHeaderIfNeeded();
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes to output stream
	 * 
	 * @param data   to be written
	 * @param offset at offset
	 * @param len    length
	 * @return this
	 * @throws IOException if errors occurred
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		generatedHeaderIfNeeded();
		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Writes to output stream
	 * 
	 * @param text to be written
	 * @return this
	 * @throws IOException if errors occurred
	 */
	public RequestContext write(String text) throws IOException {
		generatedHeaderIfNeeded();
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Generates header if it wasn't generated
	 * 
	 * @throws IOException if errors occurred
	 */
	private void generatedHeaderIfNeeded() throws IOException {
		if (headerGenerated) {
			return;
		}

		headerGenerated = true;
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + getMime() + "\r\n");
		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		outputCookies.forEach(x -> sb.append((getOneCookie(x))));
		sb.append("\r\n");
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * Generates cookie output for header
	 * 
	 * @param cookie output for header
	 * @return output for header
	 */
	private String getOneCookie(RCCookie cookie) {
		StringBuilder sbc = new StringBuilder();
		sbc.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"");

		if (cookie.getDomain() != null) {
			sbc.append("; Domain=" + cookie.getDomain());
		}

		if (cookie.getPath() != null) {
			sbc.append("; Path=" + cookie.getPath());
		}

		if (cookie.getMaxAge() != null) {
			sbc.append("; Max-Age=" + cookie.maxAge);
		}

		if (cookie.getName().equals("sid")) {
			sbc.append("; HttpOnly\r\n");
		} else {
			sbc.append("\r\n");
		}
		return sbc.toString();
	}

	/**
	 * Returns mime type with given charset if mime type is text
	 * 
	 * @return mime type
	 */
	private String getMime() {
		String mime = mimeType;
		if (mime.startsWith("text/")) {
			mime += "; charset=" + encoding;
		}

		return mime;
	}

	/**
	 * Throws an exception if header was already generated
	 * 
	 * @param obj some object
	 * @return input parameter if header wasn't true
	 */
	private <T> T requierHeaderNotGenerated(T obj) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}

		return obj;
	}

	/**
	 * Adds cookie if header wasn't generated
	 * 
	 * @param cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(requierHeaderNotGenerated(cookie));
	}

	/**
	 * Setter for encoding. If header was generated, an exception is thrown
	 * 
	 * @param encoding to set
	 * @throws RuntimeException if header was generated
	 */
	public void setEncoding(String encoding) {
		this.encoding = requierHeaderNotGenerated(encoding);
	}

	/**
	 * Setter for statusCode. If header was generated, an exception is thrown
	 * 
	 * @param statusCode to set
	 * @throws RuntimeException if header was generated
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = requierHeaderNotGenerated(statusCode);
	}

	/**
	 * Setter for statusText. If header was generated, an exception is thrown
	 * 
	 * @param statusText to set
	 * @throws RuntimeException if header was generated
	 */
	public void setStatusText(String statusText) {
		this.statusText = requierHeaderNotGenerated(statusText);
	}

	/**
	 * Setter for mimeType. If header was generated, an exception is thrown
	 * 
	 * @param mimeType to set
	 * @throws RuntimeException if header was generated
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = requierHeaderNotGenerated(mimeType);
	}

	/**
	 * Setter for contentLength. If header was generated, an exception is thrown
	 * 
	 * @param contentLength to set
	 * @throws RuntimeException if header was generated
	 */
	public void setContentLength(Long contentLength) {
		this.contentLength = requierHeaderNotGenerated(contentLength);
	}

	/**
	 * Getter for dispatcher
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Defines a cookie used by request context class
	 * 
	 * @author matfures
	 *
	 */
	public static class RCCookie {
		/**
		 * Name of cookie
		 */
		private String name;

		/**
		 * Cookies value
		 */
		private String value;

		/**
		 * Cookies domain
		 */
		private String domain;

		/**
		 * Cookies path
		 */
		private String path;

		/**
		 * Cookies maximal valid age
		 */
		private Integer maxAge;

		/**
		 * Constructor. Simply sets given values to corresponding variables without any
		 * check-ups
		 * 
		 * @param name   cookies name
		 * @param value  cookies value
		 * @param domain cookies domain
		 * @param path   cookies path
		 * @param maxAge cookies max valid age
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for max age
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
