package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Simple implementation of database. File that provides Student entries is
 * needed
 * 
 * @author Matej Fureš
 *
 */
public class StudentDB {
	public static void main(String[] args) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Couldn't read from file");
			return;
		}

		StudentDatabase db = null;

		try {
			db = new StudentDatabase(lines);
		} catch (NullPointerException e) {
			System.out.println("Invalid input");
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		Scanner s = new Scanner(System.in);
		handleQuieries(s, db);
		s.close();
	}

	/**
	 * Takes queries from input and processes them
	 * @param s scanner from whom is read
	 * @param db db that is filtered
	 */
	private static void handleQuieries(Scanner s, StudentDatabase db) {
		String line;
		QueryParser qp;
		List<StudentRecord> records;
		StudentRecord record;
		int maxJmbag = 0, maxName = 0, maxLastName = 0;

		while (true) {
			System.out.print("> ");
			line = s.nextLine();

			if (ComparisonOperators.LIKE.satisfied(line, "query ")) {
				try {
					qp = new QueryParser(line.substring(6));
				} catch (Exception e) {
					if (e.getMessage().isEmpty()) {
						System.out.println("Query is in bad format");
					} else {
						System.out.println(e.getMessage());
					}

					continue;
				}

				if (qp.isDirectQuery()) {
					System.out.println("Using index for record retrieval.");
					record = db.forJMBAG(qp.getQueriedJMBAG());

					if (record == null) {
						System.out.println("Records selected: 0\n");
						continue;
					}

					formatForProgram(record.getJmbag().length(), record.getLastName().length(),
							record.getFirstName().length());
					System.out.println("| " + record.getJmbag() + " | " + record.getLastName() + " | "
							+ record.getFirstName() + " | " + record.getFinalGrade() + " |");
					formatForProgram(record.getJmbag().length(), record.getLastName().length(),
							record.getFirstName().length());
					System.out.println("Records selected: 1\n");
				} else {
					records = db.filter(new QueryFilter(qp.getQuery()));

					if (records.isEmpty()) {
						System.out.println("Records selected: 0\n");
						continue;
					}
					maxJmbag = 0;
					maxName = 0;
					maxLastName = 0;

					for (StudentRecord sr : records) {
						if (sr.getJmbag().length() > maxJmbag) {
							maxJmbag = sr.getJmbag().length();
						}

						if (sr.getFirstName().length() > maxName) {
							maxName = sr.getFirstName().length();
						}

						if (sr.getLastName().length() > maxLastName) {
							maxLastName = sr.getLastName().length();
						}
					}

					formatForProgram(maxJmbag, maxLastName, maxName);
					for(StudentRecord sr:records) {
						formatSingleRecord(maxJmbag, maxLastName, maxName, sr);
					}
					formatForProgram(maxJmbag, maxLastName, maxName);
					System.out.println("Records selected: " + records.size() + "\n");
				}

			} else {
				if (line.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				} else {
					System.out.println("Unsupported command.\n");
				}
			}
		}
	}

	/**
	 * Simple method that provides formated output for programs user interface
	 * 
	 * @param len1 max length of jmbag
	 * @param len2 max length of firstName
	 * @param len3 max length of lastName
	 */
	private static void formatForProgram(int len1, int len2, int len3) {
		System.out.print("+");
		for (int i = 0; i < len1 + 2; i++) {
			System.out.print("=");
		}

		System.out.print("+");
		for (int i = 0; i < len2 + 2; i++) {
			System.out.print("=");
		}
		System.out.print("+");
		for (int i = 0; i < len3 + 2; i++) {
			System.out.print("=");
		}
		System.out.println("+===+");
	}

	/**
	 * Simple method that provides formated output for programs user interface for
	 * individual records
	 * 
	 * @param len1   max length of jmbag
	 * @param len2   max length of firstName
	 * @param len3   max length of lastName
	 * @param record record
	 */
	private static void formatSingleRecord(int len1, int len2, int len3, StudentRecord record) {
		System.out.print("| ");
		System.out.print(record.getJmbag());
		for (int i = record.getJmbag().length(); i < len1; i++) {
			System.out.print(" ");
		}

		System.out.print(" | ");
		System.out.print(record.getLastName());
		for (int i = record.getLastName().length(); i < len2; i++) {
			System.out.print(" ");
		}

		System.out.print(" | ");
		System.out.print(record.getFirstName());
		for (int i = record.getFirstName().length(); i < len3; i++) {
			System.out.print(" ");
		}
		
		System.out.println(" | "+record.getFinalGrade()+" |");
	}
}
