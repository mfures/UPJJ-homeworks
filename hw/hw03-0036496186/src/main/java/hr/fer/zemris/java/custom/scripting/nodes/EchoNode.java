package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node representing a command which generates some textual output dynamically.
 * It inherits from Node class
 * 
 * @author Matej Fureš
 *
 */
public class EchoNode extends Node {
	/**
	 * Elements array storing nodes elements
	 */
	private Element[] elements;

	/**
	 * Constructor. Element array elements mustn't be null, exception is thrown
	 * otherwise.
	 * 
	 * @param elements Element array to be set
	 * @throws NullPointerException if elements is null
	 */
	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements);

		this.elements = elements;
	}

	/**
	 * Method returns nodes Elements array
	 * 
	 * @return Elements array elements from this Node
	 */
	public Element[] getElements() {
		return this.elements;
	}

	/**
	 * Method to prints object Node in string representation
	 * 
	 * @return String representation of Node
	 */
	@Override
	public String toString() {
		String s = "{$ =";

		for (int i = 0; i < getElements().length; i++) {
			s += " " + getElements()[i].toString();
		}
		s += " $}";

		return s;
	}
}
