package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Simple class that defines a database of students, students can't share jmbag,
 * and finalGrades must be between 1 and 5.
 * 
 * @author Matej Fureš
 *
 */
public class StudentDatabase {
	/**
	 * Holds all StudentRecord entries in database
	 */
	private List<StudentRecord> entries;

	/**
	 * Holds all indexes of StudentRecords in entries list for fast retrieval
	 */
	private Map<String, Integer> indexes;

	/**
	 * Constructs database out of given string array
	 * 
	 * @param lines to be parsed as StudentRecords and added to database
	 * @throws NullPointerException     if lines are null, or any entry is null
	 * @throws IllegalArgumentException if lines aren't in valid format
	 */
	public StudentDatabase(List<String> lines) {
		Objects.requireNonNull(lines);

		entries = new ArrayList<>();
		indexes = new HashMap<>();

		for (String line : lines) {
			Objects.requireNonNull(line);
			StudentRecord record = parseStudentRecord(line);

			if (entries.contains(record)) {
				throw new IllegalArgumentException("Students must have unique jmbgas.");
			}

			entries.add(record);
			indexes.put(record.getJmbag(), entries.size() - 1);
		}
	}

	/**
	 * Method locates and returns StudentRecord for given jmbag in complexity O(1).
	 * If jmbag doesn't exist, null is returned.
	 * 
	 * @param jmbag to be checked for
	 * @return StudentRecord for given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Integer index = indexes.get(jmbag);

		return index == null ? null : entries.get(index);
	}

	/**
	 * Returns list of StudentRecords in database that fulfill filters accept method
	 * 
	 * @param filter whose accept method is used
	 * @return List of all entries that fulfill filters method
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();

		for (StudentRecord record : entries) {
			if (filter.accepts(record)) {
				filteredList.add(record);
			}
		}

		return filteredList;
	}

	/**
	 * Tries to parse line as StudentRecord. If it can't, or if entries are invalid,
	 * an exception is thrown
	 * 
	 * @param line to be parsed
	 * @return StudentRecord representation of line
	 * @throws IllegalArgumentException if StudenRecord can't be made from line
	 */
	private StudentRecord parseStudentRecord(String line) {
		String[] args = line.trim().split("\\s+");
		if (args.length < 4 || args.length > 5) {
			throw new IllegalArgumentException("Incorrect number of fields");
		}

		int finalgrade;

		if (args.length == 5) {
			finalgrade = getIfInt(args[4]);
			return new StudentRecord(args[0], args[1] + " " + args[2], args[3], finalgrade);
		}

		finalgrade = getIfInt(args[3]);
		return new StudentRecord(args[0], args[1], args[2], finalgrade);
	}

	/**
	 * Tries to parse line as int, if it can't throws an exception.
	 * 
	 * @param line to be parsed to int
	 * @return int representation of line
	 * @throws IllegalArgumentException if line can't be parsed
	 */
	private int getIfInt(String line) {
		try {
			return Integer.parseInt(line);
		} catch (Exception e) {
			throw new IllegalArgumentException("Couldn't parse final grade as int.");
		}
	}
}
