package hr.fer.zemris.java.hw07.observer1;

public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Number of changes observer tracks
	 */
	private int numberOfChangesTracked;

	/**
	 * Constructor
	 * 
	 * @param numberOfChangesTracked number of changes observer tracks
	 * @throws IllegalArgumentException if numberOfChangesTracked is < 1
	 */
	public DoubleValue(int numberOfChangesTracked) {
		if (numberOfChangesTracked < 1) {
			throw new IllegalArgumentException("Number of changes tracked must be positive number");
		}

		this.numberOfChangesTracked = numberOfChangesTracked;
	}

	/**
	 * Executes when value in integer storage changes
	 * 
	 * @param istorage storage that tracks integer
	 * @throws IllegalStateException if observer was done but still notified
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (numberOfChangesTracked == 0) {
			throw new IllegalStateException("Observer was notified when it did all tracking set in constructor.");
		}

		numberOfChangesTracked--;
		System.out.println("Double value: " + 2*istorage.getValue());
		
		if(numberOfChangesTracked==0) {
			istorage.removeObserver(this);
		}
	}

}
