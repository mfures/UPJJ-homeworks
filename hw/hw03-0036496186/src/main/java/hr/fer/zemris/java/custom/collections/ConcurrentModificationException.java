package hr.fer.zemris.java.custom.collections;

/**
 * Defines an exception that should be thrown when ElementsGetter is used to
 * access next element in collection that has been altered after this
 * ElementsGetter has been created.
 * 
 * @author Matej Fureš
 *
 */
public class ConcurrentModificationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new exception
	 */
	public ConcurrentModificationException() {

	}
	
	/**
	 * Creates new exception with message
	 * 
	 * @param message message to add to exception
	 */
	public ConcurrentModificationException(String message) {
		super(message);
	}
	
}
