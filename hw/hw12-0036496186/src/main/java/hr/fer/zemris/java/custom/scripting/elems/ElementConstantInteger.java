package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Simple element that has 1 String field called name.
 * 
 * @author Matej Fureš
 */
public class ElementConstantInteger extends Element {
	/**
	 * Elements value
	 */
	private int value;

	/**
	 * Constructor.
	 * 
	 * @param value int to set
	 */
	public ElementConstantInteger(int value) {

		this.value = value;
	}

	/**
	 * Getter for value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
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
