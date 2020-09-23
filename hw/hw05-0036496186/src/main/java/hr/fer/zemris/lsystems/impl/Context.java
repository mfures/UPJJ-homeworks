package hr.fer.zemris.lsystems.impl;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that tracks turtles context in stack like fashion
 * 
 * @author Matej Fureš
 *
 */
public class Context {
	/**
	 * Stack for tracking turtles states
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Constructor for Context. Initializes an empty stack.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * Returns current state from top of stack, without removing it.
	 * 
	 * @return TurtleState on top of stack
	 * @throws EmptyStackException if stack is empty
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Pushes state on top of stack. State cant be null, otherwise an exception is
	 * thrown.
	 * 
	 * @param state to be pushed
	 * @throws NullPointerException if given state is null
	 */
	public void pushState(TurtleState state) {
		Objects.requireNonNull(state);

		stack.push(state);
	}

	/**
	 * Removes TurtleState that is on top of stack, from said stack
	 * 
	 * @throws EmptyStackException if stack is empty
	 */
	public void popState() {
		stack.pop();
	}
}
