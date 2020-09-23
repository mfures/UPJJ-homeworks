package hr.fer.zemris.java.custom.collections;

import java.util.Iterator;
import java.util.Objects;

/**
 * Defines a simple hash table. Entry key musn't be null, value can be null. No
 * duplicates are stored. If given key already exist its value is overridden
 * 
 * @author Matej Fureš
 *
 * @param <K> Type of input key
 * @param <V> Type of input value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Table size used to in default constructor
	 */
	private static final int DEFAULT_TABLE_SIZE = 16;

	/**
	 * Maximum power of 2 that fits into integer range
	 */
	private static final int MAX_POWER_OF_2_FOR_INTEGER = 1073741824;

	/**
	 * Maximum fullness percentage before reallocation
	 */
	private static final double MAX_FULLNESS_PERCENTAGE = 0.75;

	/**
	 * Number of currently stored elements
	 */
	private int size;

	/**
	 * Table that stores entries
	 */
	TableEntry<K, V>[] table;

	/**
	 * Tracks number of structural modifications made to collection
	 */
	private long modificationCount;

	/**
	 * Creates table whose capacity is smallest power of number 2 bigger or equal to
	 * input capacity. If capacity is bigger than 2^30 or smaller than 1, an
	 * exception is thrown.
	 * 
	 * @param capacity used to create table
	 * @throws IllegalArgumentException if capacity isn't in correct range
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		table = new TableEntry[smallestPowerOf2BiggerOrEqualTo(capacity)];
	}

	/**
	 * Creates hash table with default size of 16
	 */
	public SimpleHashtable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * Calculates smallest power of number 2 bigger or equal to input value. If
	 * number is bigger than 2^30 or smaller than 1, an exception is thrown.
	 * 
	 * @param n parameter used in calculation
	 * @return smallest power of number 2 bigger or equal to input value
	 * @throws IllegalArgumentException if n is out of range
	 */
	private int smallestPowerOf2BiggerOrEqualTo(int n) {
		if (n < 1 || n > MAX_POWER_OF_2_FOR_INTEGER) {
			throw new IllegalArgumentException();
		}

		n--;
		int powerOfTwo = 1;

		while (n != 0) {
			n /= 2;
			powerOfTwo *= 2;
		}

		return powerOfTwo;
	}

	/**
	 * Adds the pair into hash map. If pair with given key already exist its old
	 * value is replaced with new. Key mus be null, an exception is throw otherwise.
	 * 
	 * @param key   entries key
	 * @param value entries value
	 * @throws NullPointerException if key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);

		int slotIndex = Math.abs(key.hashCode()) % table.length;

		if (table[slotIndex] == null) {
			size++;
			table[slotIndex] = new TableEntry<>(key, value);

			reallocateIfOverfilled();
			modificationCount++;

			return;
		}

		TableEntry<K, V> currentElement = table[slotIndex];

		while (currentElement.next != null) {
			if (currentElement.key.equals(key)) {
				currentElement.value = value;
				return;
			}

			currentElement = currentElement.next;
		}

		if (currentElement.key.equals(key)) {
			currentElement.value = value;
		} else {
			TableEntry<K, V> newEntry = new TableEntry<>(key, value);
			currentElement.next = newEntry;
			size++;

			modificationCount++;
			reallocateIfOverfilled();
		}
	}

	/**
	 * If current table is overfilled it is reallocated, otherwise does nothing
	 */
	private void reallocateIfOverfilled() {
		if ((size * 1.0 / table.length) > MAX_FULLNESS_PERCENTAGE) {
			reallocateArray();
		}
	}

	/**
	 * Removes all entries from table
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		modificationCount++;
		size = 0;
	}

	/**
	 * Returns the object that is stored in backing array with given key. If key
	 * doesn't exist in map, null is returned.
	 * 
	 * @param index of element we want to access
	 * @return element located at position index
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int slotIndex = Math.abs(key.hashCode()) % table.length;

		if (table[slotIndex] == null) {
			return null;
		}

		TableEntry<K, V> currentElement = table[slotIndex];

		do {
			if (currentElement.key.equals(key)) {
				return currentElement.value;
			}

			currentElement = currentElement.next;
		} while (currentElement != null);

		return null;
	}

	/**
	 * Returns number of elements currently stored in this hash map
	 * 
	 * @return number of elements currently stored
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Method tries to find given key in this hash map, and if it finds it, returns
	 * true. If key isn't present returns false.
	 * 
	 * @param key key that is searched for
	 * @return true if hash map contains key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int slotIndex = Math.abs(key.hashCode()) % table.length;

		if (table[slotIndex] == null) {
			return false;
		}

		TableEntry<K, V> currentElement = table[slotIndex];

		do {
			if (currentElement.key.equals(key)) {
				return true;
			}

			currentElement = currentElement.next;
		} while (currentElement != null);

		return false;
	}

	/**
	 * Method tries to find given value in this hash map, and if it finds it,
	 * returns true. If key isn't present returns false.
	 * 
	 * @param value value that is searched for
	 * @return true if hash map contains value, false otherwise
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> currentElement = null;

		for (int i = 0; i < size; i++) {
			if (table[i] == null) {
				continue;
			}

			currentElement = table[i];

			do {
				if (currentElement.value.equals(value)) {
					return true;
				}

				currentElement = currentElement.next;
			} while (currentElement != null);
		}

		return false;
	}

	/**
	 * Removes entry with given key from collection. If it doesn't exist, it does
	 * nothing.
	 * 
	 * @param key whose entry is removed
	 */
	public void remove(Object key) {
		if (!containsKey(key)) {
			return;
		}

		modificationCount++;
		size--;

		int slotIndex = Math.abs(key.hashCode()) % table.length;

		if (table[slotIndex].key.equals(key)) {
			table[slotIndex] = table[slotIndex].next;

			return;
		}

		TableEntry<K, V> previousElement = table[slotIndex];
		TableEntry<K, V> currentElement = table[slotIndex].next;// can't be null

		do {
			if (currentElement.key.equals(key)) {
				previousElement.next = currentElement.next;
				break;
			}

			previousElement = currentElement;
			currentElement = currentElement.next;
		} while (currentElement != null);
	}

	/**
	 * This method returns true only if this hash map contains 0 elements.
	 * 
	 * @return true only if collection contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns string representation of hash maps entries
	 * 
	 * @return string representation of map
	 */
	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}

		String s = "[";

		int currentRow = 0;
		while (table[currentRow] == null) {
			currentRow++;
		}

		s += table[currentRow].toString();
		s += rowOfEntriesToString(table[currentRow].next);

		currentRow++;

		while (currentRow < table.length) {
			s += rowOfEntriesToString(table[currentRow]);
			currentRow++;
		}

		s += "]";
		return s;
	}

	/**
	 * Method that returns all elements of tables row in format for toString method
	 * 
	 * @param element that is first in row
	 * @return string representation of row
	 */
	private String rowOfEntriesToString(TableEntry<K, V> element) {
		String s = "";

		while (element != null) {
			s += ", " + element.toString();
			element = element.next;
		}

		return s;
	}

	/**
	 * Reallocates current table array to new array with double length
	 */
	private void reallocateArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = new TableEntry[table.length * 2];
		TableEntry<K, V> currentElement = null, nextElement = null;

		for (int i = 0; i < size; i++) {
			if (table[i] == null) {
				continue;
			}

			currentElement = table[i];
			nextElement = currentElement.next;

			while (currentElement != null) {
				currentElement.next = null;
				addTableEntry(currentElement, newTable);

				currentElement = nextElement;
				nextElement = currentElement == null ? null : currentElement.next;
			}
		}

		table = newTable;
	}

	/**
	 * Method adds existing entries to table
	 * 
	 * @param entry    that is added
	 * @param newTable to which entry is added
	 */
	private void addTableEntry(TableEntry<K, V> entry, TableEntry<K, V>[] newTable) {

		int slotIndex = Math.abs(entry.key.hashCode()) % newTable.length;

		if (newTable[slotIndex] == null) {
			newTable[slotIndex] = entry;
			return;
		}

		TableEntry<K, V> currentElement = newTable[slotIndex];

		while (currentElement.next != null) {
			currentElement = currentElement.next;
		}

		currentElement.next = entry;
	}

	/**
	 * Creates and returns iterator for iteration over hash map
	 * 
	 * @return iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Defines a simple table entry. Key can't be null
	 * 
	 * @author Matej Fureš
	 *
	 * @param <K> Type of input key
	 * @param <V> Type of input value
	 */
	public static class TableEntry<K, V> {
		/**
		 * Value of key
		 */
		private K key;

		/**
		 * Value of value
		 */
		private V value;

		/**
		 * Refers to next table entry in slot
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor. If key is null exception is thrown.
		 * 
		 * @param key   value of key
		 * @param value value of value
		 * @throws NullPointerException if key is null
		 */
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key);

			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for value
		 * 
		 * @return value of value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for value
		 * 
		 * @param value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for key
		 * 
		 * @return value of key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns string representation of table entry
		 */
		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}

	/**
	 * Simple nested Iterator class that implements Iterator
	 * 
	 * @author Matej Fureš
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Entry that is currently handled
		 */
		private TableEntry<K, V> currentEntry;

		/**
		 * Next entry for iteration
		 */
		private TableEntry<K, V> nextEntry;

		/**
		 * Current row of table
		 */
		private int currentRow;

		/**
		 * Iterators counter of modifications over hash map
		 */
		private long iteratorModificationCount;

		/**
		 * Constructor that initializes values
		 */
		private IteratorImpl() {
			iteratorModificationCount = modificationCount;

			nextEntry = table[0] == null ? findNextNonNullTableRowEntry() : table[0];
		}

		/**
		 * Returns true only if iterator has next element
		 * 
		 * @return true if next element is available
		 * @throws ConcurrentModificationException if map has been altered from outside
		 */
		public boolean hasNext() {
			requireModificationCountUnchanged();

			return nextEntry != null;
		}

		/**
		 * Returns next element in hash map
		 * 
		 * @return next entry
		 * @throws NoSuchElementException          if there are no more entries
		 * @throws ConcurrentModificationException if map has been altered from outside
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			requireModificationCountUnchanged();

			if (nextEntry == null) {
				throw new NoSuchElementException();
			}

			currentEntry = nextEntry;

			if (nextEntry.next != null) {
				nextEntry = nextEntry.next;
			} else {
				nextEntry = findNextNonNullTableRowEntry();
			}

			return currentEntry;
		}

		/**
		 * Removes element that was returned previously from collection. If element
		 * became null, an exception is thrown.
		 * 
		 * @throws IllegalStateException           if current entry became null
		 *                                         previously
		 * @throws ConcurrentModificationException if map has been altered from outside
		 */
		public void remove() {
			requireModificationCountUnchanged();

			if (currentEntry == null) {
				throw new IllegalStateException();
			}

			SimpleHashtable.this.remove(currentEntry.key);
			iteratorModificationCount++;
			currentEntry = null;
		}

		/**
		 * Finds first non null row in table after current row and returns first entry
		 * in said row
		 * 
		 * @return entry in first next non null row
		 */
		private SimpleHashtable.TableEntry<K, V> findNextNonNullTableRowEntry() {
			currentRow++;

			while (currentRow < table.length) {
				if (table[currentRow] != null) {
					return table[currentRow];
				}

				currentRow++;
			}

			return null;
		}

		/**
		 * Throws an exception if hash map has been altered from other sources than this
		 * iterator
		 * 
		 * @throws ConcurrentModificationException if map has been altered from outside
		 */
		private void requireModificationCountUnchanged() {
			if (iteratorModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
