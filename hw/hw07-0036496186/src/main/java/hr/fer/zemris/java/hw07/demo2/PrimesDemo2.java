package hr.fer.zemris.java.hw07.demo2;

/**
 * Example 2 of collection
 * 
 * @author Matej Fureš
 *
 */
public class PrimesDemo2 {
	/**
	 * Example
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
