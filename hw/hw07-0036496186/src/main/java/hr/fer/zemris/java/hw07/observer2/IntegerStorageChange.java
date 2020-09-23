package hr.fer.zemris.java.hw07.observer2;

import java.util.Objects;

/**
 * Encapsulation of integer storage used for giving more details to observer
 * classes
 * 
 * @author Matej Fureš
 *
 */
public class IntegerStorageChange {
	/**
	 * Storage of integers
	 */
	private IntegerStorage istorage;

	/**
	 * Current value stored in storage
	 */
	private int currentValue;

	/**
	 * Previous value stored in storage
	 */
	private int oldValue;

	/**
	 * Constructor. Istorage mustan't be null.
	 * 
	 * @param istorage storage
	 * @param oldValue old value
	 * @throws NullPointerException if istorage is null
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue) {
		Objects.requireNonNull(istorage);

		this.istorage = istorage;
		this.currentValue = istorage.getValue();
		this.oldValue = oldValue;
	}

	/**
	 * Returns value of istorage
	 * 
	 * @return istorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Returns value of currentValue
	 * 
	 * @return currentValue
	 */
	public int getCurrentValue() {
		return currentValue;
	}

	/**
	 * Returns value of oldValue
	 * 
	 * @return oldValue
	 */
	public int getOldValue() {
		return oldValue;
	}
}
