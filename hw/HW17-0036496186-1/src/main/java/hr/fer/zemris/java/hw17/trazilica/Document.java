package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines a simple document
 * 
 * @author mfures
 *
 */
public class Document {
	/**
	 * Documents location or null
	 */
	private Path path;

	/**
	 * Representation of document
	 */
	private Vector tfIdf;

	/**
	 * TF representation
	 */
	private Map<String, Integer> tf = new LinkedHashMap<>();

	/**
	 * Measure of similarity
	 */
	private double similar;

	/**
	 * Constructor
	 * 
	 * @param stopWords lines
	 * @param lines     with words
	 */
	public Document(List<String> stopWords, List<String> lines) {
		for (String line : lines) {
			var words = getWords(line);
			for (String word : words) {
				if (word.isEmpty())
					continue;
				if (stopWords.contains(word.toLowerCase()))
					continue;
				tf.merge(word.toLowerCase(), 1, (old, newV) -> old + 1);
			}
		}
	}

	/**
	 * Constructor
	 * 
	 * @param stopWords list
	 * @param path      location of document to read from
	 * @throws IOException if errors occurred
	 */
	public Document(List<String> stopWords, Path path) throws IOException {
		this(stopWords, Files.readAllLines(path));
		this.path = path;
	}

	/**
	 * Parser for words
	 * 
	 * @param line to be parsed
	 * @return parsed words from line
	 */
	private List<String> getWords(String line) {
		List<String> list = new ArrayList<>();
		char[] data = line.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (Character.isAlphabetic(data[i])) {
				sb.append(data[i]);
			} else {
				if (sb.length() != 0) {
					list.add(sb.toString());
					sb.setLength(0);
				}
			}
		}

		if (sb.length() != 0) {
			list.add(sb.toString());
		}
		return list;
	}

	/**
	 * Tries to read lines from set path
	 * 
	 * @return List of lines
	 * @throws IOException if errors occurred
	 */
	public List<String> readLines() throws IOException {
		return Files.readAllLines(path);
	}

	/**
	 * Getter for path
	 * 
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Returns vocabulary
	 * 
	 * @return vocabulary
	 */
	public Set<String> getVocabulary() {
		return tf.keySet();
	}

	/**
	 * TF value for given word
	 * 
	 * @param word to check
	 * @return TF value
	 */
	public Integer getTf(String word) {
		return tf.get(word);
	}

	/**
	 * Getter for tfIdf
	 * 
	 * @return tfIdf
	 */
	public Vector getTfIdf() {
		return tfIdf;
	}

	/**
	 * Setter for tfIdf
	 * 
	 * @param tfIdf to be set
	 */
	public void setTfIdf(Vector tfIdf) {
		this.tfIdf = tfIdf;
	}

	/**
	 * Getter for similar
	 * 
	 * @return similar
	 */
	public double getSimilar() {
		return similar;
	}

	/**
	 * Setter for similar
	 * 
	 * @param similar to be set
	 */
	public void setSimilar(double similar) {
		this.similar = similar;
	}
}
