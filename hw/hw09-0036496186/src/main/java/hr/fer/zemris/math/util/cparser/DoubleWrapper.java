package hr.fer.zemris.math.util.cparser;

/**
 * Wrapper for 1 double value with flags used in parsing number. Number value is
 * unchangeable, but flags aren't
 * 
 * @author matfures
 *
 */
public class DoubleWrapper {
	/**
	 * Stores value
	 */
	private double value;

	/**
	 * Stores information if number was signed, important for + with positive
	 * numbers
	 */
	private boolean isSigned;

	/**
	 * Stores information if number is imaginary (e.g. 3i)
	 */
	private boolean isImaginary;

	/**
	 * Getter for isSigned flag
	 * @return isSigned flag
	 */
	public boolean isSigned() {
		return isSigned;
	}
	/**
	 * Setter for isSigned flag
	 * @param isSigned flag
	 */
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}

	/**
	 * Getter for isImaginary flag
	 * @return isImaginary flag
	 */
	public boolean isImaginary() {
		return isImaginary;
	}

	/**
	 * Setter for isImaginary flag
	 * @param isImaginary flag
	 */
	public void setImaginary(boolean isImaginary) {
		this.isImaginary = isImaginary;
	}

	/**
	 * Getter for value
	 * @return value
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Setter for value
	 * @param value to be set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
