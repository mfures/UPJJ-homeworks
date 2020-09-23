package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Simple element that has 1 String field called name.
 * 
 * @author Matej Fureš
 */
public class ElementConstantDouble extends Element {
	/**
	 * Elements value
	 */
	private double value;

	/**
	 * Constructor.
	 * 
	 * @param value double to set
	 */
	public ElementConstantDouble(double value) {

		this.value = value;
	}

	/**
	 * Overrides Elements method to return this elements value variable
	 * 
	 * @return String value of value
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
