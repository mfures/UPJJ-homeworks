package hr.fer.zemris.java.gui.layouts;

/**
 * Exception to be thrown while an extraordinary situations occur while using
 * calcLayout
 * 
 * @author matfures
 *
 */
public class CalcLayoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception with set message
	 * 
	 * @param text to be sent as message
	 */
	public CalcLayoutException(String text) {
		super(text);
	}
}
