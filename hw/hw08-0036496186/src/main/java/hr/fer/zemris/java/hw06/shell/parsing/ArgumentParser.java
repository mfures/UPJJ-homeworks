package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Parser for arguments
 * 
 * @author Matej Fureš
 *
 */
public class ArgumentParser {
	/**
	 * List of arguments
	 */
	private List<String> arguments;

	/**
	 * Lexer for input
	 */
	private ArgumentLexer lexer;

	/**
	 * Input to be parsed. Mustn't be null.
	 * 
	 * @param input to be parsed
	 * @throws NullPointerException if input is null
	 */
	public ArgumentParser(String input) {
		Objects.requireNonNull(input);

		arguments = new ArrayList<>();
		lexer = new ArgumentLexer(input);
		parse();
	}

	/**
	 * Parses input and adds strings to list
	 */
	private void parse() {
		String current = lexer.getNext();

		while (current != null) {
			arguments.add(current);

			current = lexer.getNext();
		}
	}

	/**
	 * Arguments on index position
	 * 
	 * @param index of position
	 * @return argument
	 */
	public String get(int index) {
		if (index < 0 || index > numOfArgs() - 1) {
			throw new IllegalArgumentException("Invalid position");
		}

		return arguments.get(index);
	}

	/**
	 * Returns number of arguments
	 * 
	 * @return number of arguments
	 */
	public int numOfArgs() {
		return arguments.size();
	}
}
