package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Simple console class used for searching trough files
 * 
 * @author mfures
 *
 */
public class Konzola {

	/**
	 * List of stop words
	 */
	private static List<String> stopWords;

	/**
	 * List of documents used as base
	 */
	private static List<Document> documents = new ArrayList<>();

	/**
	 * Vocabulary of documents
	 */
	private static Set<String> vocabulary = new LinkedHashSet<>();

	/**
	 * IDF vector
	 */
	private static Map<String, Double> idfV = new HashMap<>();

	/**
	 * Result of query
	 */
	private static List<Document> queryResult;

	/**
	 * Starts console and loads needed data
	 * 
	 * @param args path to documents
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments provided. Expected 1.");
			System.exit(1);
		}

		Path documentsP = checkPath(args[0]);

		try {
			stopWords = Files.readAllLines(Paths.get("src/main/resources/hrvatski_stoprijeci.txt"));
			Files.walkFileTree(documentsP, new FileVisitor<>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Konzola.documents.add(new Document(stopWords, file));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			badPath();
		}

		fillVocabulary();

		for (String word : vocabulary) {
			int n = 0;
			for (Document doc : documents) {
				if (doc.getTf(word) != null) {
					n++;
				}
			}

			idfV.put(word, Math.log((double) documents.size() / n));
		}

		for (Document doc : documents) {
			Vector tfIdf = new Vector();
			for (String word : vocabulary) {
				int tf = zeroOrInt(doc.getTf(word));
				tfIdf.add(tf * idfV.get(word));
			}
			doc.setTfIdf(tfIdf);
		}

		console();
	}

	/**
	 * Returns i if i is not null, 0 otherwise
	 * 
	 * @param i integer to be checked
	 * @return i or 0 if i is null
	 */
	private static int zeroOrInt(Integer i) {
		if (i == null) {
			return 0;
		}

		return i;
	}

	/**
	 * FIlls vocabulary
	 */
	private static void fillVocabulary() {
		for (Document doc : documents) {
			vocabulary.addAll(doc.getVocabulary());
		}
	}

	/**
	 * Checks if provided path is valid
	 * 
	 * @param args said path
	 * @return path if is valid
	 */
	private static Path checkPath(String args) {
		Path documents = null;
		try {
			documents = Paths.get(args);
		} catch (Exception e) {
			badPath();
		}

		if (!Files.isDirectory(documents)) {
			badPath();
		}
		return documents;
	}

	/**
	 * Writes a message to user and shuts down program
	 */
	private static void badPath() {
		System.out.println("Invalid path provided");
		System.exit(1);
	}

	/**
	 * Acts as a console
	 */
	private static void console() {
		System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");
		Scanner sc = new Scanner(System.in);
		String line;
		while (true) {
			System.out.print("Enter command > ");
			line = sc.nextLine();
			if (line.isEmpty())
				continue;
			if (line.equals("exit"))
				break;
			if (line.equals("results")) {
				if (queryResult == null) {
					System.out.println("Nije pokrenut nit jedan query zahtjev.");
				} else {
					printResult();
				}
				System.out.println();
				continue;
			}

			String[] arr = line.split("\\s+");
			switch (arr[0]) {
			case "query":
				handleQuery(arr);
				break;
			case "type":
				if (queryResult == null) {
					System.out.println("Nije pokrenut nit jedan query zahtjev.");
					break;
				}
				if (arr.length != 2) {
					System.out.println("Nepoznata naredba.");
					break;
				}

				int n;
				try {
					n = Integer.parseInt(arr[1]);
				} catch (Exception e) {
					System.out.println("Nepoznata naredba.");
					break;
				}
				if (n < 0 || n >= queryResult.size()) {
					System.out.println("Nepoznata naredba.");
					break;
				}

				Document doc = queryResult.get(n);
				System.out.println("Dokument: " + doc.getPath());
				System.out.println("----------------------------------------------------------------");
				try {
					List<String> lines = doc.readLines();
					for (String lineT : lines) {
						System.out.println(lineT);
					}
				} catch (IOException ignored) {
				}

				System.out.println("----------------------------------------------------------------");
				break;
			default:
				System.out.println("Nepoznata naredba.");
			}

			System.out.println();
		}
		sc.close();
	}

	/**
	 * Handles query
	 * 
	 * @param arr arguments
	 */
	private static void handleQuery(String[] arr) {
		List<String> input = new ArrayList<>();
		for (int i = 1; i < arr.length; i++) {
			input.add(arr[i]);
		}

		Document search = new Document(stopWords, input);
		Vector tfIdf = new Vector();
		for (String word : vocabulary) {
			int tf = zeroOrInt(search.getTf(word));
			tfIdf.add(tf * idfV.get(word));
		}
		search.setTfIdf(tfIdf);

		queryResult = new ArrayList<>();
		for (Document doc : documents) {
			double sim = doc.getTfIdf().cos(search.getTfIdf());
			if (sim > 0) {
				doc.setSimilar(sim);
				queryResult.add(doc);
			}
		}

		queryResult.sort((x, y) -> (Double.compare(y.getSimilar(), x.getSimilar())));

		System.out.println("Query is: " + search.getVocabulary());
		System.out.println("Najboljih 10 rezultata:");
		printResult();
	}

	/**
	 * Prints result
	 */
	private static void printResult() {
		for (int i = 0; i < Math.min(10, queryResult.size()); i++) {
			System.out.printf("[%2d] (%.4f) %s\r\n", i, queryResult.get(i).getSimilar(), queryResult.get(i).getPath());
		}
	}
}
