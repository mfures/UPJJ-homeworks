package hr.fer.zemris.java.custom.collections;

/**
 * Represents general collection of objects. Contains general methods for
 * handling of collections.
 * 
 * @author Matej Fureš
 *
 */
public class Collection {
	/**
	 * Returns the number of currently stored objects in this collections. This
	 * method always returns 0.
	 * 
	 * @return number of currently stored objects in this collections
	 */
	public int size() {
		return 0;
	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * 
	 * @return true if collection contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Adds the given object into this collection. This method is empty.
	 * 
	 * @param value object you want to add to collection
	 */
	public void add(Object value) {
		// empty
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * equals method. This method always returns false.
	 * 
	 * @param value we are searching for
	 * @return true only if the collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it. This method always returns false
	 * 
	 * @param value we wish to remove from collection
	 * @return true only if the collection contains given element
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null. This implementation throws UnsupportedOperationException.
	 * 
	 * @return Array containing all elements of collection
	 * @throws UnsupportedOperationException always
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process(.) for each element of this collection. This
	 * implementation does nothing.
	 * 
	 * @param processor which operation is used on all elements
	 */
	public void forEach(Processor processor) {
		// empty
	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged.
	 * 
	 * @param other collection which elements are added
	 */
	public void addAll(Collection other) {
		if (other.isEmpty()) {
			return;
		}

		/**
		 * Local processor class whose method process will add each item into the
		 * current collection by calling method add
		 * 
		 * @author Matej Fureš
		 *
		 */
		class LocalAddProcessor extends Processor {

			/**
			 * Method that adds item to the collection
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new LocalAddProcessor());
	}

	/**
	 * Removes all elements from this collection. Override this method.
	 */
	public void clear() {
		// empty
	}
}
