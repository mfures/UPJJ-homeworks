package hr.fer.zemris.java.hw06.shell;

/**
 * Generic exception that should be thrown whenever some error happens in shells
 * work
 * 
 * @author Matej Fureš
 *
 */
public class ShellIOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public ShellIOException() {

	}

	/**
	 * Constructor that takes in some message to be set
	 * 
	 * @param message message to be set
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
