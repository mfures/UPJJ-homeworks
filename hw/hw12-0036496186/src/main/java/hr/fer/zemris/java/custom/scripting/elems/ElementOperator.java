package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Simple element that has 1 String field called name.
 * 
 * @author Matej Fureš
 */
public class ElementOperator extends Element {
	/**
	 * Elements value
	 */
	private String symbol;

	/**
	 * Constructor. Arguments mustn't be null, otherwise exception is thrown.
	 * 
	 * @param value String to set
	 * @throws NullPointerException if value is null
	 */
	public ElementOperator(String symbol) {
		Objects.requireNonNull(symbol);

		this.symbol = symbol;
	}

	/**
	 * Overrides Elements method to return this elements value variable
	 * 
	 * @return String value
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
