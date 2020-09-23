package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a storage of integers that tracks changes and has observers
 * 
 * @author Matej Fureš
 *
 */
public class IntegerStorage {
	/**
	 * Tracked value
	 */
	private int value;

	/**
	 * Observers that are observing value
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	/**
	 * Constructor
	 * 
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Adds observer into storage if it doesn't contain it already. If observer is
	 * null, exception is thrown
	 * 
	 * @param observer to be added
	 * @throws NullPointerException if observer is null
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes observer from storage if it contain it . If observer is null,
	 * exception is thrown
	 * 
	 * @param observer to be removed
	 * @throws NullPointerException if observer is null
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Removes all observers from storage
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns tracked value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets value to given value. If value changes, observers are notified
	 * 
	 * @param value to be set
	 */
	public void setValue(int value) {
		if (this.value != value) {
			int oldvalue=value;
			this.value = value;
			if (observers != null) {
				List<IntegerStorageObserver> copy=new ArrayList<IntegerStorageObserver>(observers);
				IntegerStorageChange change=new IntegerStorageChange(this, oldvalue);
				for (IntegerStorageObserver observer : copy) {
					observer.valueChanged(change);
				}
			}
		}
	}
}