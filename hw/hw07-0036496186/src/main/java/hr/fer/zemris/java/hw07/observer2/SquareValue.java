package hr.fer.zemris.java.hw07.observer2;

/**
 * Tracks integer storage, and if its value changes, prints square value of it
 * to standard output
 * 
 * @author Matej Fureš
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange change) {
		int value = change.getCurrentValue();

		System.out.println("Provided new value: " + value + ", square is " + value * value);
	}

}
