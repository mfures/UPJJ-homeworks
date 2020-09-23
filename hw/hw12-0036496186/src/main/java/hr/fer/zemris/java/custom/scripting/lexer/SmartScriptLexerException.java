package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Generic exception that should be thrown whenever lexer tries to parse
 * something that isn't allowed
 * 
 * @author Matej Fureš
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public SmartScriptLexerException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}