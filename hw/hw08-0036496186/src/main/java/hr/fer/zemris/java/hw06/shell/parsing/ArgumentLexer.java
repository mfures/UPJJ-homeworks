package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.Objects;

/**
 * Lexer used for parsing arguments
 * 
 * @author Matej Fureš
 *
 */
public class ArgumentLexer {
	/**
	 * Stores input data as char array
	 */
	private char[] data;

	/**
	 * Stores current argument
	 */
	private StringBuilder current;

	/**
	 * Tracks current index
	 */
	private int currentIndex;

	/**
	 * Constructor. Input mustn't be null
	 * 
	 * @param input to be parsed
	 */
	public ArgumentLexer(String input) {
		Objects.requireNonNull(input);

		data = input.toCharArray();
		current = new StringBuilder();
	}

	public String getNext() {
		skipTabsAndSpaces();

		if (currentIndex > data.length) {
			throw new LexerException("Illegal call for next");
		}

		if (currentIndex == data.length) {
			currentIndex++;
			return null;
		}

		return getNextArgument();
	}

	/**
	 * Gets next argument
	 * 
	 * @return next argument
	 */
	private String getNextArgument() {
		if (data[currentIndex] == '"') {
			if (isClosed()) {
				currentIndex++;
				return getNextArgumentFromString();
			}
		}

		while (!isBreakPoint(0)) {
			current.append(data[currentIndex]);
			
			currentIndex++;
		}

		String arg = current.toString();
		current.setLength(0);
		return arg;
	}

	/**
	 * Checks if string is closed
	 * 
	 * @return true if string is closed
	 */
	private boolean isClosed() {
		for (int i = currentIndex + 1; i < data.length; i++) {
			if (isStringTerminator(i))
				return true;
		}

		return false;
	}

	/**
	 * Returns true if is string terminator
	 * 
	 * @param position to be checked
	 * @return true if string end
	 */
	private boolean isStringTerminator(int position) {
		if (data[position] == '"') {
			if (data[position - 1] != '\\') {
				return true;
			}
		}

		return false;
	}

	private String getNextArgumentFromString() {
		while (!isStringTerminator(currentIndex)) {
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\') {
					currentIndex++;
					current.append(data[currentIndex]);
				} else if (data[currentIndex + 1] == '"') {
					currentIndex++;
					current.append(data[currentIndex]);
				} else {
					current.append(data[currentIndex]);
					currentIndex++;
					current.append(data[currentIndex]);
				}
			} else {
				current.append(data[currentIndex]);
			}

			currentIndex++;
		}
		currentIndex++;

		String arg = current.toString();
		current.setLength(0);
		return arg;
	}

	/**
	 * Skips tabs and spaces
	 */
	private void skipTabsAndSpaces() {
		while (currentIndex < data.length) {
			if (isBreakPoint(0)) {
				currentIndex++;
			} else {
				break;
			}
		}
	}

	/**
	 * Checks if position is skippable
	 * 
	 * @param offset from current index
	 * @return true if skippable
	 */
	private boolean isBreakPoint(int offset) {
		if (currentIndex + offset >= data.length) {
			return true;
		}

		if (data[currentIndex + offset] == ' ' || data[currentIndex + offset] == '\t') {
			return true;
		}

		return false;
	}
}
