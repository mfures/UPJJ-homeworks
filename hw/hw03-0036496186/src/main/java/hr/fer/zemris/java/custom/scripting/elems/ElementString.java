package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Simple element that has 1 String field called name.
 * 
 * @author Matej Fureš
 */
public class ElementString extends Element {
	/**
	 * Elements value
	 */
	private String value;

	/**
	 * Constructor. Arguments mustn't be null, otherwise exception is thrown.
	 * 
	 * @param value String to set
	 * @throws NullPointerException if value is null
	 */
	public ElementString(String value) {
		Objects.requireNonNull(value);

		this.value = value;
	}

	/**
	 * Overrides Elements method to return this elements value variable
	 * 
	 * @return String value
	 */
	@Override
	public String asText() {
		return value;
	}

	/**
	 * Method to prints object Element in string representation
	 * 
	 * @return String representation of Element
	 */
	@Override
	public String toString() {
		String s = String.valueOf('"');

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '"') {
				s += '\\';
			}

			if (value.charAt(i) == '\\') {
				s += '\\';
			}

			s += String.valueOf(value.charAt(i));
		}

		s += s = String.valueOf('"');
		return s;
	}
}
