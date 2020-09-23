package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represents general collection of objects. Contains general methods for
 * handling of collections.
 * 
 * @author Matej Fureš
 *
 */
public interface Collection<T> {
	/**
	 * Implement this method to return the number of currently stored objects in
	 * this collections.
	 * 
	 * @return number of currently stored objects in this collections
	 */
	int size();

	/**
	 * This method to return true if collection contains no objects and false
	 * otherwise.
	 * 
	 * @return true if collection contains no objects and false otherwise
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Implement this method to add the given object into this collection.
	 * 
	 * @param value object you want to add to collection
	 */
	void add(T value);

	/**
	 * Implement this method to return true only if the collection contains given
	 * value, as determined by equals method.
	 * 
	 * @param value we are searching for
	 * @return true only if the collection contains given value
	 */
	boolean contains(Object value);

	/**
	 * Implement this method to return true only if the collection contains given
	 * value as determined by equals method and removes one occurrence of it.
	 * 
	 * @param value we wish to remove from collection
	 * @return true only if the collection contains given element
	 */
	boolean remove(Object value);

	/**
	 * Implement this method to allocate a new array with size equals to the size of
	 * this collections, fills it with collection content and returns the array.
	 * This method should never returns null.
	 * 
	 * @return Array containing all elements of collection
	 * @throws UnsupportedOperationException always
	 */
	Object[] toArray();

	/**
	 * This method to calls processor.process(.) for each element of this
	 * collection. If given processor is null, an exception is thrown.
	 * 
	 * @param processor which operation is used on all elements
	 * @throws NullPointerException if processor is null
	 */
	default void forEach(Processor<? super T> processor) {
		Objects.requireNonNull(processor);

		ElementsGetter<T> getter = createElementsGetter();

		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}

	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged. If implementation of
	 * this interface tracks modifications, modification counter should
	 * increase(given that the collection is not empty). Throws an exception if
	 * given collection is null
	 * 
	 * @param other collection which elements are added
	 * @throws NullPointerException if other is null
	 */
	default void addAll(Collection<? extends T> other) {
		Objects.requireNonNull(other);

		if (other.isEmpty()) {
			return;
		}

		Processor<T> p = value -> add((T) value);
		other.forEach(p);
	}

	/**
	 * Implement this method to remove all elements from this collection. Override
	 * this method.
	 */
	void clear();

	/**
	 * Implement this method to create a new ElementsGetter which is used to cycles
	 * elements in the collection.
	 * 
	 * @return ElementsGetter for current collection
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * Method adds into the current collection all elements from the given
	 * collection if they pass the test set in Tester. This other collection remains
	 * unchanged. If implementation of this interface tracks modifications,
	 * modification counter should increase(given that the collection is not empty).
	 * Throws an exception if given collection or tester is null
	 * 
	 * @param col    collection which elements are added
	 * @param tester Tester which test method is used on elements of collection
	 * @throws NullPointerException if other is null
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		Objects.requireNonNull(col);
		Objects.requireNonNull(tester);

		var getter = col.createElementsGetter();

		while (getter.hasNextElement()) {
			T current = (T) getter.getNextElement();

			if (tester.test(current)) {
				add(current);
			}
		}
	}

}
