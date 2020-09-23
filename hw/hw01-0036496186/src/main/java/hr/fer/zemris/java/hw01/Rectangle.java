package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Ovaj program služi za izračun vrijednosti površine i opsega pravokutnika uz
 * dvije zadane stranice. Program se pokreće ili bez argumenata ili sa dva
 * argumenta. Ako se pokrene bez argumenata, program čita vrijednosti s
 * tipkovnice dok ne učita dvije pozitivne vrijednosti. Ako se pokrene sa dva
 * argumenta očekuje da se radi o dva pozitivna broja ili se gasi.
 * 
 * @author Matej Fureš
 *
 */
public class Rectangle {
	/**
	 * Metoda main poziva metode za obradu ulaznih podataka i metodu za ispis
	 * krajnjeg riješenja .
	 * 
	 * @param args dva pozitivna broja, ili ništa
	 */
	public static void main(String[] args) {
		if ((args.length != 0) && (args.length != 2)) {
			System.out.println("Ovaj program potrebno je pokrenuti sa 0 ili 2 argumenta iz naredbenog retka!");
			return;
		}
		Double width, height;
		checkArgsCastable("3");
		if (args.length == 2) {
			if (checkArgsCastable(args) == false) {
				return;
			}

			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);

			if (checkValidity(width, height) == false) {
				return;
			}
		} else {
			Scanner scan = new Scanner(System.in);

			width = getSide("širinu", scan);
			height = getSide("visinu", scan);

			scan.close();
		}

		System.out.println(solve(width, height));
	}

	/**
	 * Metoda služi za davanje odgovarajućih instrukcija korisniku i za čitanje
	 * linija sa tipkovnice koje šalje na obradu dok ne obradi pozitivan broj.
	 * 
	 * @param sideName Ime stranice čiju vrijednost se traži od korisnika
	 * @param scan     Scanner koji čita sa System.in-a
	 * @return Prvi pozitivan broj pročitan s ulaza
	 */
	private static Double getSide(String sideName, Scanner scan) {

		System.out.print("Unesite " + sideName + " > ");
		String line = scan.nextLine();
		while (checkBadLine(line)) {
			System.out.print("Unesite " + sideName + " > ");
			line = scan.nextLine();
		}

		return Double.parseDouble(line);
	}

	/**
	 * Metoda šalje ulaznu liniju na provjeru valjanosti. Provjerava je li ulaz
	 * moguće pretvoriti u double, a zatim je li broj pozitivan.
	 * 
	 * @param line String koji provjeravamo
	 * @return true ako je string pozitivan broj, inače false
	 */
	private static boolean checkBadLine(String line) {
		if (checkArgsCastable(line) == false)
			return true;

		if (checkValidity(Double.parseDouble(line)) == false)
			return true;

		return false;
	}

	/**
	 * Metoda pokušava pretvoriti ulazne stringove u tip double.
	 * 
	 * @param args Stringovi za provjeru.
	 * @return true ako su svi ulazni stringovi brojevi, inače false
	 */
	private static boolean checkArgsCastable(String... args) {
		for (String line : args) {
			try {
				Double.parseDouble(line);
			} catch (NumberFormatException e) {
				System.out.println("'" + line + "' se ne može protumačiti kao broj.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Metoda provjerava jesu li svi ulazni brojevi pozitivni.
	 * 
	 * @param args Brojevi za provjeru.
	 * @return true ako su svi ulazni brojevi pozitivni, false inače
	 */
	private static boolean checkValidity(Double... args) {
		for (Double temp : args) {
			if (temp > 0) {
				continue;
			}

			if (temp < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
			} else {
				System.out.println("Unijeli ste 0.");
			}
			return false;
		}
		return true;
	}

	/**
	 * Metoda za generiranje Stringa za ispis podataka o pravokutniku.
	 * 
	 * @param width  Vrijednost širine pravokutnika
	 * @param height Vrijednost visine pravokutnika
	 * @return String za ispis
	 */
	public static String solve(Double width, Double height) {
		return "Pravokutnik širine " + width + " i visine " + height + " ima površinu " + calculateArea(width, height)
				+ " te opseg " + calculatePerimeter(width, height) + ".";
	}

	/**
	 * Metoda izračunava vrijednost opsega za pravokutnik opisan stranicama width i
	 * height.
	 * 
	 * @param width  Vrijednost širine pravokutnika
	 * @param height Vrijednost visine pravokutnika
	 * @return Vrijednost opsega pravokutnika
	 * @throws IllegalArgumentException za negativne ulazne vrijednosti
	 */
	public static Double calculatePerimeter(Double width, Double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Unešeni brojevi moraju biti pozitivni!");
		}

		Double perimeter = 2 * width + 2 * height;
		return perimeter;
	}

	/**
	 * Metoda izračunava vrijednost površine za pravokutnik opisan stranicama width
	 * i height.
	 * 
	 * @param width  Vrijednost širine pravokutnika
	 * @param height Vrijednost visine pravokutnika
	 * @return Vrijednost površine pravokutnika
	 * @throws IllegalArgumentException za negativne ulazne vrijednosti
	 */
	public static Double calculateArea(Double width, Double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Unešeni brojevi moraju biti pozitivni!");
		}

		Double area = width * height;
		return area;
	}

}
