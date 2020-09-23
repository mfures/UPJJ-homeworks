package hr.fer.zemris.java.hw05.db;

/**
 * Defines a functional interface whose method extracts one field of given
 * student record
 * 
 * @author Matej Fureš
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns defined field of student record
	 * 
	 * @param record record whose field is extracted
	 * @return records field as string
	 */
	String get(StudentRecord record);
}
