package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Simple implementation of multistack as defined in homework assignment
 * 
 * @author Matej Fureš
 *
 */
public class ObjectMultistack {
	/**
	 * Stores entries
	 */
	private Map<String, TableEntry> map;

	/**
	 * Constructor
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Pushes wrapper to top of given row. If any argument is null, an exception is
	 * thrown.
	 * 
	 * @param keyName      key for entry
	 * @param valueWrapper entries value
	 * @throws NullPointerException if any of arguments is null
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName, "Key mustn't be null");
		Objects.requireNonNull(keyName, "Values wrapper mustn't be null");

		TableEntry entry = new TableEntry(valueWrapper);
		entry.next = map.get(keyName);

		map.put(keyName, entry);
	}

	/**
	 * Returns top element in row and removes it from stack
	 * 
	 * @param keyName key for row
	 * @return first element in row
	 * @throws NoSuchElementException if there is no entry for given key
	 */
	public ValueWrapper pop(String keyName) {
		TableEntry top = map.get(keyName);

		requiereContains(top);

		map.remove(keyName);
		map.put(keyName, top.next);

		return top.value;
	}

	/**
	 * Returns top element in row without removing it from stack
	 * 
	 * @param keyName key for row
	 * @return first element in row
	 * @throws NoSuchElementException if there is no entry for given key
	 */
	public ValueWrapper peek(String keyName) {
		TableEntry top = map.get(keyName);

		requiereContains(top);

		return top.value;
	}

	/**
	 * Checks if given argument is null, and if so, throws an exception
	 * 
	 * @param top to be checked for null
	 * @throws NoSuchElementException if given argument is null
	 */
	private void requiereContains(TableEntry top) {
		if (top == null) {
			throw new NoSuchElementException("Stack doesn't contain element with given key.");
		}
	}

	/**
	 * Checks if table row has 0 entries and returns true if so, false otherwise
	 * 
	 * @param keyName row of table to check
	 * @return
	 */
	public boolean isEmpty(String keyName) {
		return map.get(keyName) == null;
	}

	/**
	 * Defines a table entry
	 * 
	 * @author Matej Fureš
	 *
	 */
	private static class TableEntry {
		/**
		 * Next entry
		 */
		private TableEntry next;

		/**
		 * Current entry
		 */
		private ValueWrapper value;

		public TableEntry(ValueWrapper value) {
			this.value = value;
		}
	}
}
