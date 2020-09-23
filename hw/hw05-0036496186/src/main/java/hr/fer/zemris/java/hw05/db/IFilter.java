package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface for validating records, used for filtration of said
 * records
 * 
 * @author Matej Fureš
 *
 */
public interface IFilter {
	/**
	 * Method returns true if said record fulfills set condition
	 * 
	 * @param record to be validated
	 * @return true if record passes given test, false otherwise
	 */
	boolean accepts(StudentRecord record);
}
