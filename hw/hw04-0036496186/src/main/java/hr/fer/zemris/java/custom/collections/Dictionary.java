package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Simple dictionary implementation that provides all simple operations that
 * work with dictionaries
 * 
 * @author Matej Fureš
 *
 * @param <K> Type of value used for keys
 * @param <V> Type of value used for values
 */
public class Dictionary<K, V> {
	/**
	 * Simple static class that defines how we couple values. Values are coupled as
	 * (R value1, S value2).
	 * 
	 * @author Matej Fureš
	 *
	 * @param <R> Type of value used for value1
	 * @param <S> Type of value used for value2
	 */
	private static class Pair<R, S> {
		/**
		 * R typed value
		 */
		private R value1;

		/**
		 * S typed value
		 */
		private S value2;

		/**
		 * Simple constructor that sets values accordingly
		 * 
		 * @param value1
		 * @param value2
		 */
		private Pair(R value1, S value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
	}

	/**
	 * Collection used to store values
	 */
	private ArrayIndexedCollection<Pair<K, V>> storage;

	/**
	 * Simple constructor that creates Dictionary
	 */
	public Dictionary() {
		storage = new ArrayIndexedCollection<Dictionary.Pair<K, V>>();
	}

	/**
	 * Checks if dictionary is empty
	 * 
	 * @return True only if dictionary has no elements, False otherwise
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/**
	 * Returns number of currently stored values
	 * 
	 * @return size of dictionary
	 */
	public int size() {
		return storage.size();
	}

	/**
	 * Removes all elements from dictionary
	 */
	public void clear() {
		storage.clear();
	}

	/**
	 * Puts pair of key and value in dictionary. If key value is already present in
	 * dictionary its value is replaced with new value. If key is null, exception is
	 * thrown.
	 * 
	 * @param key   key value for entry
	 * @param value values value for entry
	 * @throws NullPointerException if key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);

		Pair<K, V> pair = new Pair<>(key, value);

		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).value1.equals(key)) {
				storage.remove(i);

				break;
			}
		}

		storage.add(pair);
	}

	/**
	 * Returns value that is placed in table with given key. If key isn't present in
	 * dictionary, an exception is thrown
	 * 
	 * @param key key whose value is returned
	 * @return value of pair with key value
	 * @throws NoSuchElementException if key doesn't exist in dictionary
	 */
	public V get(Object key) {
		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).value1.equals(key)) {
				return storage.get(i).value2;
			}
		}

		throw new NoSuchElementException();
	}
}
