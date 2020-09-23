package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing an entire document. It inherits from Node class
 * 
 * @author Matej Fureš
 *
 */
public class DocumentNode extends Node {

	/**
	 * Method to prints object Node in string representation
	 * 
	 * @return String representation of Node
	 */
	@Override
	public String toString() {
		String originalDocument = "";

		for (int i = 0; i < numberOfChildren(); i++) {
			originalDocument += getChild(i).toString();
		}

		return originalDocument;
	}
}
