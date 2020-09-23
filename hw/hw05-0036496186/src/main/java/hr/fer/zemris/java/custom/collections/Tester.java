package hr.fer.zemris.java.custom.collections;

/**
 * Provides testing of a given object by conditions set in test method.
 * 
 * @author Matej Fureš
 *
 */
public interface Tester<T> {
	/**
	 * Implement this method to return true if its testing condition is true
	 * 
	 * @param obj Object to be tested
	 * 
	 * @return true only if Object passed the test
	 */
	boolean test(T obj);
}
