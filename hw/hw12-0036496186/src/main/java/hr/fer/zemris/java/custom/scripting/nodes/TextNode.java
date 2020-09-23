package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Node representing a piece of textual data. It inherits from Node class.
 * 
 * @author Matej Fureš
 *
 */
public class TextNode extends Node {
	/**
	 * Nodes text
	 */
	private String text;

	/**
	 * Constructor. Argument text is required to be non null, otherwise exception is
	 * thrown
	 * 
	 * @param text String to be set
	 * @throws NullPointerException if text is null
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text);

		this.text = text;
	}

	/**
	 * Returns Nodes text variable as a String
	 * 
	 * @return Nodes text variable
	 */
	public String getText() {
		return text;
	}

	/**
	 * Method to prints object Node in string representation
	 * 
	 * @return String representation of Node
	 */
	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '{') {
				s += '\\';
			}

			if (text.charAt(i) == '\\') {
				s += '\\';
			}

			s += String.valueOf(text.charAt(i));
		}

		return s;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
