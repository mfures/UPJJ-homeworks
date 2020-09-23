package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Defines an primes collection that calculates primes up to nth prime defined
 * in constructor. Collections values are accessible only trough iteration
 * 
 * @author Matej Fureš
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Number of primes this collection can produce
	 */
	private int n;

	/**
	 * Constructor
	 * 
	 * @param n number of primes to generate
	 */
	public PrimesCollection(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("N must be greater than 0.");
		}

		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator(n);
	}

	/**
	 * Simple iterator implementation used for iteration trough our primes
	 * collection
	 * 
	 * @author Matej
	 *
	 */
	private static class PrimesIterator implements Iterator<Integer> {
		/**
		 * Tracks how many iterations are left
		 */
		private int counter;

		/**
		 * Tracks last prime given to user
		 */
		private int lastPrime = 1;

		/**
		 * Constructor
		 * 
		 * @param counter of primes to be produced
		 */
		public PrimesIterator(int counter) {
			this.counter = counter;
		}

		@Override
		public boolean hasNext() {
			return counter != 0;
		}

		@Override
		public Integer next() {
			boolean isPrime;

			if (counter == 0) {
				throw new NoSuchElementException("No more elements are left in collection.");
			}

			counter--;

			for (int i = lastPrime + 1; i < Integer.MAX_VALUE; i++) {
				isPrime = true;

				for (int j = 2; j <= Math.sqrt(i); j++) {
					if (i % j == 0) {
						isPrime = false;
						break;
					}
				}

				if (isPrime) {
					lastPrime = i;
					return lastPrime;
				}
			}

			throw new IllegalStateException("Program should've returned a prime.");
		}

	}
}
