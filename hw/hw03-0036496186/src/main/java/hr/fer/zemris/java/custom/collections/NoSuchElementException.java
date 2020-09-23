package hr.fer.zemris.java.custom.collections;

/**
 * Defines an exception that should be thrown when element getter is required
 * to return next element in the collection and there are no more elements
 * 
 * @author Matej
 *
 */
public class NoSuchElementException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public NoSuchElementException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public NoSuchElementException(String message) {
		super(message);
	}
}
