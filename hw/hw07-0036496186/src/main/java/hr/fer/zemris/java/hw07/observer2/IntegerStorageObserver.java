package hr.fer.zemris.java.hw07.observer2;

/**
 * Observer of integer storage
 * 
 * @author Matej Fureš
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Executes when value in integer storage changes
	 * 
	 * @param change encapsulation of storage that tracks integer and its old value
	 */
	public void valueChanged(IntegerStorageChange change);
}