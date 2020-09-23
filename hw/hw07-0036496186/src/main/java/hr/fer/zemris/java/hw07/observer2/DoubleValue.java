package hr.fer.zemris.java.hw07.observer2;

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
	 * @param change encapsulation of storage that tracks integer and its old value
	 * @throws IllegalStateException if observer was done but still notified
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		if (numberOfChangesTracked == 0) {
			throw new IllegalStateException("Observer was notified when it did all tracking set in constructor.");
		}

		numberOfChangesTracked--;
		System.out.println("Double value: " + 2*change.getCurrentValue());
		
		if(numberOfChangesTracked==0) {
			change.getIstorage().removeObserver(this);
		}
	}

}
