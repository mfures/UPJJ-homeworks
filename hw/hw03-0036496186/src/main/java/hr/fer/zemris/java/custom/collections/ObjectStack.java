package hr.fer.zemris.java.custom.collections;

/**
 * Provides a simple implementation of stack data structure and all supporting
 * operations.
 * 
 * @author Matej Fureš
 *
 */
public class ObjectStack {
	/**
	 * Collection that stores elements on stack
	 */
	private ArrayIndexedCollection elementStorage;

	/**
	 * Default constructor. Returns an empty stack.
	 */
	public ObjectStack() {
		this.elementStorage = new ArrayIndexedCollection();
	}

	/**
	 * Method returns true only if stack has no elements, otherwise returns false.
	 * 
	 * @return true true only if stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return elementStorage.isEmpty();
	}

	/**
	 * Returns number of elements currently stored on stack
	 * 
	 * @return Number of currently stored elements
	 */
	public int size() {
		return elementStorage.size();
	}

	/**
	 * Pushes given value on the stack. Does not accept null value, throws an
	 * exception.
	 * 
	 * @param value to be pushed on stack
	 * @throws NullPointerException value is null
	 */
	public void push(Object value) {
		elementStorage.add(value);
	}

	/**
	 * Removes last value pushed on stack from stack and returns it. If the stack is
	 * empty when method pop is called, the method should throw EmptyStackException.
	 * 
	 * @return element last pushed element
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		Object top = peek();
		elementStorage.remove(elementStorage.size() - 1);
		return top;
	}

	/**
	 * Returns last value pushed on stack from stack. If the stack is empty when
	 * method peek is called, the method should throw EmptyStackException.
	 * 
	 * @return element last pushed element
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if (elementStorage.isEmpty()) {
			throw new EmptyStackException();
		}

		Object top = elementStorage.get(elementStorage.size() - 1);
		return top;
	}

	/**
	 * Removes all elements from stack
	 */
	public void clear() {
		elementStorage.clear();
	}
}
