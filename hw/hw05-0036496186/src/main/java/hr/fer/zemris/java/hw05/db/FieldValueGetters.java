package hr.fer.zemris.java.hw05.db;

/**
 * Provides some implementations of {@link IFieldValueGetter} interface for
 * working with StudentRecords
 * 
 * @author Matej Fureš
 *
 */
public class FieldValueGetters {
	/**
	 * Extracts first name out of given StudentRecord
	 */
	public static final IFieldValueGetter FIRST_NAME = (x) -> x.getFirstName();
	
	/**
	 * Extracts last name out of given StudentRecord
	 */
	public static final IFieldValueGetter LAST_NAME = (x) -> x.getLastName();
	
	/**
	 * Extracts Jmbag out of given StudentRecord
	 */
	public static final IFieldValueGetter JMBAG = (x) -> x.getJmbag();

}
