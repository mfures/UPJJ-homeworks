package hr.fer.zemris.java.custom.collections;

/**
 * Defines an interface that adds methods which are suited for working with
 * lists to Collection interface
 * 
 * @author Matej Fureš
 *
 */
public interface List<T> extends Collection<T> {
	/**
	 * Implement this method to return object located at position index inside of
	 * the list
	 * 
	 * @param index index of element to be returned
	 * @return element on position index
	 */
	T get(int index);

	/**
	 * Implement this method to insert object on given position.
	 * 
	 * @param value    object to be inserted
	 * @param position position on which object is inserted
	 */
	void insert(T value, int position);

	/**
	 * Implement this method to return the position of first occurrence of Object
	 * value inside of the list. Implement this method to return -1 if object value
	 * isn't found in the list.
	 * 
	 * @param value to find in the list
	 * @return position of first occurrence of the object in the list, or -1
	 */
	int indexOf(Object value);

	/**
	 * Implement this method to remove object on position index from the list.
	 * 
	 * @param index postion of the element that should be removed from the list
	 */
	void remove(int index);
}
