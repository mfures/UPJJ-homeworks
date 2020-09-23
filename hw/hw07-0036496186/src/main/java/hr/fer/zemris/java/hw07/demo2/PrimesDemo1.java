package hr.fer.zemris.java.hw07.demo2;

/**
 * Example 1 of collection
 * 
 * @author Matej Fureš
 *
 */
public class PrimesDemo1 {
	/**
	 * Example
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			 System.out.println("Got prime: " + prime);
		}

		System.out.println();

	}
}