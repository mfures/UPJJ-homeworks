package hr.fer.zemris.java.hw06.shell.parsing;

/**
 * Exception that parser throws when it encounters some exceptional situation
 * 
 * @author Matej Fureš
 *
 */
public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public ParserException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public ParserException(String message) {
		super(message);
	}
}
