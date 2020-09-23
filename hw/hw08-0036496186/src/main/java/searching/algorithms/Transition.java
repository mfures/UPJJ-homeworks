package searching.algorithms;

/**
 * Defines a transition between two states
 * 
 * @author Matej Fureš
 *
 * @param <S> of state
 */
public class Transition<S> {
	/**
	 * State
	 */
	private S state;

	/**
	 * Cost of transition
	 */
	private double cost;

	/**
	 * Constructor
	 * 
	 * @param state of transition
	 * @param cost  of transition
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
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
