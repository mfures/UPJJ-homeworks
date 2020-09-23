package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Generic exception that should be thrown whenever lexer tries to parse
 * something that isn't allowed
 * 
 * @author Matej Fureš
 *
 */
public class LexerException extends RuntimeException{
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
