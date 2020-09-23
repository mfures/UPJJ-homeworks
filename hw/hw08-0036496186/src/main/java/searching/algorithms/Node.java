package searching.algorithms;

import java.util.Objects;

/**
 * Node as used in searching algorithms
 * 
 * @author Matej Fureš
 *
 * @param <S> for node
 */
public class Node<S> {
	/**
	 * Parent node
	 */
	private Node<S> parent;

	/**
	 * Current state
	 */
	private S state;

	/**
	 * States cost
	 */
	private double cost;

	/**
	 * Constructor. State mustn't be null
	 * 
	 * @param parent of node
	 * @param state  this state
	 * @param cost   of state
	 * @throws NullPointerException if state is null
	 */
	public Node(Node<S> parent, S state, double cost) {
		Objects.requireNonNull(state);

		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for parent
	 * 
	 * @return parent
	 */
	public Node<S> getParent() {
		return parent;
	}

	/**
	 * Getter for state
	 * 
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter for cost
	 * 
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
}
