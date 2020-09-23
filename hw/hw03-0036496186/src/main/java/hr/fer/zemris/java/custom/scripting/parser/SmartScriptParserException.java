package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Generic exception that should be thrown whenever parser tries to parse
 * something that isn't allowed
 * 
 * @author Matej Fureš
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public SmartScriptParserException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
