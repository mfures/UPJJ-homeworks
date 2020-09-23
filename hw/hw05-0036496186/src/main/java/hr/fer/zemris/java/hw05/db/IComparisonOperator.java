package hr.fer.zemris.java.hw05.db;

/**
 * Defines an functional interface with method satisfied that determines
 * correlation of two given strings
 * 
 * @author Matej Fureš
 *
 */
public interface IComparisonOperator {
	/**
	 * Checks if set condition is satisfied, and if it is returns true
	 * 
	 * @param value1 first operator
	 * @param value2 second operator
	 * @return true if condition is satisfied
	 */
	boolean satisfied(String value1, String value2);
}
