package hr.fer.zemris.math.util.cparser;

/**
 * General exception to be thrown when encountered with exceptional situation
 * during any process of complex number parsing
 * 
 * @author matfures
 *
 */
public class ComplexNumberParsingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param text to be set on exception message
	 */
	public ComplexNumberParsingException(String text) {
		super(text);
	}
}
