package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Simple element that has 1 String field called name.
 * 
 * @author Matej Fureš
 */
public class ElementFunction extends Element {
	/**
	 * Elements name
	 */
	private String name;

	/**
	 * Constructor. Arguments mustn't be null, otherwise exception is thrown.
	 * 
	 * @param name String to set
	 * @throws NullPointerException if name is null
	 */
	public ElementFunction(String name) {
		Objects.requireNonNull(name);

		this.name = name;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Overrides Elements method to return this elements name variable
	 * 
	 * @return String name
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
}
