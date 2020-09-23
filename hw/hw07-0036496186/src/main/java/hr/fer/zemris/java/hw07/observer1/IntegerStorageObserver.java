package hr.fer.zemris.java.hw07.observer1;

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
	 * @param istorage storage that tracks integer
	 */
	public void valueChanged(IntegerStorage istorage);
}