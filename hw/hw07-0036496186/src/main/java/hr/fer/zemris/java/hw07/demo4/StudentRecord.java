package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Defines a student
 * 
 * @author Matej Fureš
 *
 */
public class StudentRecord {
	/**
	 * Students id
	 */
	private String jmbag;

	/**
	 * Students name
	 */
	private String name;

	/**
	 * Students last name
	 */
	private String lastName;

	/**
	 * Points that student scored on first exam
	 */
	private double miPoints;

	/**
	 * Points that student scored on second exam
	 */
	private double ziPoints;

	/**
	 * Points that student scored on laboratory exam
	 */
	private double lvPoints;

	/**
	 * Students grade
	 */
	private int grade;

	/**
	 * Creates student, arguments cant be null
	 * 
	 * @param jmbag    id
	 * @param name     students name
	 * @param lastName students last name
	 * @param miPoints students exam points
	 * @param ziPoints students exam points
	 * @param lvPoints students exam points
	 * @param grade    students grade
	 * @throws NullPointerException if strings are null
	 */
	public StudentRecord(String jmbag, String name, String lastName, double miPoints, double ziPoints, double lvPoints,
			int grade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(name);
		Objects.requireNonNull(lastName);

		this.jmbag = jmbag;
		this.name = name;
		this.lastName = lastName;
		this.miPoints = miPoints;
		this.ziPoints = ziPoints;
		this.lvPoints = lvPoints;
		this.grade = grade;
	}

	/**
	 * Getter for jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for miPoints
	 * 
	 * @return miPoints
	 */
	public double getMiPoints() {
		return miPoints;
	}

	/**
	 * Getter for ziPoints
	 * 
	 * @return ziPoints
	 */
	public double getZiPoints() {
		return ziPoints;
	}

	/**
	 * Getter for lvPoints
	 * 
	 * @return lvPoints
	 */
	public double getLvPoints() {
		return lvPoints;
	}

	/**
	 * Getter for grade
	 * 
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return jmbag + '\t' + name + '\t' + lastName + '\t' + miPoints + '\t' + ziPoints + '\t' + lvPoints + '\t'
				+ grade;
	}
}
