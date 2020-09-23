package searching.slagalica;

import java.util.Arrays;
import java.util.Objects;

/**
 * Defines a state of puzzle
 * 
 * @author Matej Fureš
 *
 */
public class KonfiguracijaSlagalice {
	/**
	 * Stores puzzles state
	 */
	private int[] data;

	/**
	 * Constructor. Data mustn't be null
	 * 
	 * @param data array
	 */
	public KonfiguracijaSlagalice(int[] data) {
		Objects.requireNonNull(data);

		this.data = data;
	}

	/**
	 * Returns copy of array
	 * 
	 * @return copy of data
	 */
	public int[] getPolje() {
		return Arrays.copyOf(data, data.length);
	}

	/**
	 * Returns index of 0 in data array. If there is no 0, an exception is thrown
	 * 
	 * @return position of zero
	 * @throws IllegalStateException if there is no 0
	 */
	public int indexOfSpace() {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0) {
				return i;
			}
		}

		throw new IllegalStateException("No 0 found in state.");
	}

	@Override
	public String toString() {
		String s = data[0] + " " + data[1] + " " + data[2] + "\r\n" + data[3] + " " + data[4] + " " + data[5] + "\r\n"
				+ data[6] + " " + data[7] + " " + data[8];
		return s.replace('0', '*');
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}
	
}
