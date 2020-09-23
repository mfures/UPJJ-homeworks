package hr.fer.zemris.java.hw06.shell.parsing;

/**
 * Exception that lexer throws when it encounters some exceptional situation
 * 
 * @author Matej Fureš
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public LexerException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public LexerException(String message) {
		super(message);
	}
}
