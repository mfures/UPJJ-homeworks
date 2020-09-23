package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Defines a simple node that has collection that stores its children, and
 * method to operate with said children.
 * 
 * @author Matej Fureš
 *
 */
public abstract class Node {
	/**
	 * Collection that stores nodes children
	 */
	private ArrayIndexedCollection myChildrenCollection;

	/**
	 * Adds a child node to current node and puts it on last place.
	 * 
	 * @param child Node to place
	 */
	public void addChildNode(Node child) {
		if (myChildrenCollection == null) {
			myChildrenCollection = new ArrayIndexedCollection();
		}

		myChildrenCollection.add(child);
	}

	/**
	 * Returns number of children that this node has
	 * 
	 * @return current nodes number of children
	 */
	public int numberOfChildren() {
		if (myChildrenCollection == null) {
			return 0;
		}

		return myChildrenCollection.size();
	}

	/**
	 * Returns child on position index of current node. If index is invalid,
	 * exception is thrown.
	 * 
	 * @param index child nodes position
	 * @return node on position index
	 * @throws IndexOutOfBoundsException if index doesn't exist
	 */
	public Node getChild(int index) {
		if (myChildrenCollection == null) {
			throw new IndexOutOfBoundsException();
		}

		return (Node) myChildrenCollection.get(index);
	}

	/**
	 * Used for traversing nodes with visitor
	 * 
	 * @param visitor that visits
	 */
	public abstract void accept(INodeVisitor visitor);
}
