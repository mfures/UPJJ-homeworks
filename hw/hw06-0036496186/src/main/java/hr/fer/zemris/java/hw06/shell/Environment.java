package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Defines some environment in which our class should operate
 * 
 * @author Matej Fureš
 *
 */
public interface Environment {
	/**
	 * Method should read line, or lines if needed, from input
	 * 
	 * @return string containing users input
	 * @throws ShellIOException thrown if error happens
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method should write one line to the user
	 * 
	 * @param text to be written to user
	 * @throws ShellIOException is thrown if error happens
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method should write one line to the user with a new line at the end
	 * 
	 * @param text to be written to user
	 * @throws ShellIOException is thrown if error happens
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns sorted unmodifiable sorted map that contains all commands usable in
	 * the shell
	 * 
	 * @return sorted map containing commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Method returns currently used multi lines Symbol
	 * 
	 * @return returns current multi lines Symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets currently used multi lines Symbol to given value
	 * 
	 * @param symbol to be set
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Method returns currently used prompt Symbol
	 * 
	 * @return returns current prompt Symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets currently used prompt Symbol to given value
	 * 
	 * @param symbol to be set
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Method returns currently used more lines Symbol
	 * 
	 * @return returns current more lines Symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets currently used more lines Symbol to given value
	 * 
	 * @param symbol to be set
	 */
	void setMorelinesSymbol(Character symbol);
}
