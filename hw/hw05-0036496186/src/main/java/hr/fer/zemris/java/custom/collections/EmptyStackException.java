package hr.fer.zemris.java.custom.collections;

/**
 * Simple implementation of an exception that should be thrown when accesed
 * stack tries to pop an element but is empty.
 * 
 * @author Matej Fureš
 *
 */
public class EmptyStackException extends RuntimeException {
	/**
	 * Creates new exception
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Creates new exception with message
	 * 
	 * @param message message to add to exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
