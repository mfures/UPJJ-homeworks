package hr.fer.zemris.java.hw07.observer1;

/**
 * Tracks integer storage, and if its value changes, prints number of changes of
 * value to standard output
 * 
 * @author Matej Fureš
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counts number of changes to storage
	 */
	private long counter = 0;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
