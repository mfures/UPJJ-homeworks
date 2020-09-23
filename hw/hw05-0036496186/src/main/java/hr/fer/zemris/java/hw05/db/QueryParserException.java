package hr.fer.zemris.java.hw05.db;

/**
 * Generic exception that should be thrown whenever parser can't process input
 * 
 * @author Matej Fureš
 *
 */
public class QueryParserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public QueryParserException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public QueryParserException(String message) {
		super(message);
	}
}
