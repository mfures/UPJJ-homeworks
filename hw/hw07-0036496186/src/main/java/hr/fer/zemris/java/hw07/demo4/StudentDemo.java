package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstration of working with streams on collections
 * 
 * @author Matej Fureš
 *
 */
public class StudentDemo {
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("studenti.txt"));
		} catch (IOException e) {
			System.out.println("Couldn't read from file");
			System.exit(1);
		}

		List<StudentRecord> records = convert(lines);

		System.out.println("Zadatak 1\r\n=========");
		System.out.println(vratiBodovaViseOd25(records));
		System.out.println("\nZadatak 2\r\n=========");
		System.out.println(vratiBrojOdlikasa(records));
		System.out.println("\nZadatak 3\r\n=========");
		vratiListuOdlikasa(records).forEach(System.out::println);
		System.out.println("\nZadatak 4\r\n=========");
		vratiSortiranuListuOdlikasa(records).forEach(System.out::println);
		System.out.println("\nZadatak 5\r\n=========");
		vratiPopisNepolozenih(records).forEach(System.out::println);
		System.out.println("\nZadatak 6\r\n=========");
		Map<Integer, List<StudentRecord>> map = razvrstajStudentePoOcjenama(records);
		for (int key : map.keySet()) {
			System.out.println("Ocjena " + key + ":" + map.get(key).size());
		}
		System.out.println("\nZadatak 7\r\n=========");
		Map<Integer, Integer> map2 = vratiBrojStudenataPoOcjenama(records);
		for (int key : map2.keySet()) {
			System.out.println("Ocjena " + key + ":" + map2.get(key));
		}

		System.out.println("\nZadatak 8\r\n=========");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for (boolean key : prolazNeprolaz.keySet()) {
			System.out.println("Prolaz " + key + ":" + prolazNeprolaz.get(key).size());
		}
	}

	/**
	 * Turns all valid lines into students
	 * 
	 * @param lines list of lines to be parsed
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> students = new ArrayList<>();
		String[] params;
		for (String line : lines) {
			if (line.isEmpty()) {
				continue;
			}

			params = line.split("\\t");
			try {
				students.add(new StudentRecord(params[0], params[1], params[2], Double.parseDouble(params[3]),
						Double.parseDouble(params[4]), Double.parseDouble(params[5]), Integer.parseInt(params[6])));
			} catch (Exception e) {
				System.out.println("Invalid input format");
				System.exit(1);
			}
		}

		return students;
	}

	/**
	 * Returns number of students that have sum of points grater than 25
	 * 
	 * @param records to be filtered
	 * @return number of students
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(x -> (x.getMiPoints() + x.getZiPoints() + x.getLvPoints()) > 25).count();
	}

	/**
	 * Returns number of students that have grade 5
	 * 
	 * @param records to be filtered
	 * @return number of students
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(x -> x.getGrade() == 5).count();
	}

	/**
	 * Returns list of students that have grade 5
	 * 
	 * @param records to be filtered
	 * @return list of students
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(x -> x.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns sorted list of students that have grade 5;sorted by points
	 * 
	 * @param records to be filtered
	 * @return sorted list of students
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(x -> x.getGrade() == 5)
				.sorted((x, y) -> Double.compare((x.getMiPoints() + x.getZiPoints() + x.getLvPoints()),
						(y.getMiPoints() + y.getZiPoints() + y.getLvPoints())))
				.collect(Collectors.toList());
	}

	/**
	 * Returns sorted list of jmbags that have grade 1;sorted by jmbag
	 * 
	 * @param records to be filtered
	 * @return sorted list of students
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(x -> x.getGrade() == 1).map(StudentRecord::getJmbag).sorted(String::compareTo)
				.collect(Collectors.toList());
	}

	/**
	 * Returns map of list of student records with grade as key
	 * 
	 * @param records to be filtered
	 * @return map of lists of students
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Returns map of number of student records with grade as key
	 * 
	 * @param records to be filtered
	 * @return map of students
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, z -> 1, (x, y) -> x + y));
	}

	/**
	 * Returns map of lists of students where keys are true and false, and indicate
	 * students that have or haven't passed
	 * 
	 * @param records to be filtered
	 * @return sorted list of students
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(x -> x.getGrade() != 1));
	}
}
