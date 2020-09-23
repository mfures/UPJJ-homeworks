package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Defines a very simple element
 * 
 * @author Matej Fureš
 *
 */
public class Element {
	/**
	 * Default implementation of this method always returns "". Override this method
	 * to return something else, if u wish.
	 * 
	 * @return String ""
	 */
	public String asText() {
		return "";
	}

	/**
	 * Method to prints object Element in string representation
	 * 
	 * @return String representation of Element
	 */
	@Override
	public String toString() {
		return asText();
	}
}
