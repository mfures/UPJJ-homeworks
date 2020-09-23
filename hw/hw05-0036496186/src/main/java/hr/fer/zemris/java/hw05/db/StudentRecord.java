package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Defines a simple student record, each student has unique jmbag
 * 
 * @author Matej Fureš
 *
 */
public class StudentRecord {
	/**
	 * Students unique jmbga, id
	 */
	private String jmbag;

	/**
	 * Students last name
	 */
	private String lastName;

	/**
	 * Students first name
	 */
	private String firstName;

	/**
	 * Students final grade
	 */
	private int finalGrade;

	/**
	 * Constructor. Jmbag, lastName and firstName mustn't be null. Final grade must
	 * be >= 1 and <=5.
	 * 
	 * @param jmbag      students jmbag
	 * @param lastName   students last name
	 * @param firstName  students first name
	 * @param finalGrade students final grade
	 * @throws NullPointerException     if jmbag, firstName or lastName is null
	 * @throws IllegalArgumentException if finalGrade is >5 or <1
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(firstName);

		if (finalGrade > 5 || finalGrade < 1) {
			throw new IllegalArgumentException("Final grade should be between 1 and 5.");
		}

		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Method returns value of jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Method returns value of lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method returns value of firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method returns value of finalGrade
	 * 
	 * @return finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Two studentRecords are considered equal if they share value of jmbag
	 * 
	 * @return true if two StudentRecors share jmbag, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return jmbag+" "+lastName+" "+firstName+" "+finalGrade;
	}
}
