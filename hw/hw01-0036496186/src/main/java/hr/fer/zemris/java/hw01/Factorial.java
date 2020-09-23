package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Ovaj program služi za izračun vrijednosti faktorijela za n, gdje je n između
 * 3 i 20. Program se pokreće bez argumenata, a preko tipkovnice prima
 * vrijednosti za izračun. Za neodgovarajuće podatke program ispisuje poruku.
 * Program se prekida kada korisnik unese poruku "kraj".
 * 
 * @author Matej Fureš
 *
 */
public class Factorial {
	/**
	 * Metoda main čita podatke sa tipkovnice pa te podatke prosljeđuje metodi
	 * handleLine na obradu u skladu sa opisom rada programa.
	 * 
	 * @param args Nema ulaznih podataka
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Ovaj program potrebno je pokrenuti bez dodatnih argumenata!");
			return;
		}

		Scanner scan = new Scanner(System.in);

		System.out.print("Unesite broj > ");
		String line = scan.nextLine();
		while (handleLine(line)) {
			System.out.print("Unesite broj > ");
			line = scan.nextLine();
		}

		scan.close();
	}

	/**
	 * Metoda za obradu linija sa tipkovnice. Metoda ispisuje poruke u skladu sa
	 * opisom programa, a cijele brojeve šalje na obradu metodi factorialCalculator.
	 * 
	 * @param line String za koji želimo utvrditi vrijednost faktorijela.
	 * @return Ako je line jednak "kraj" vraća false, inače true.
	 */
	private static boolean handleLine(String line) {

		if (line.equals("kraj")) {
			System.out.println("Doviđenja.");
			return false;
		}

		try {
			int n = Integer.parseInt(line);
			if (n > 2 && n < 21) {
				System.out.println(n + "! = " + factorialCalcululator(n));
			} else {
				System.out.println("'" + n + "' nije broj u dozvoljenom rasponu.");
			}
		} catch (NumberFormatException e) {
			System.out.println("'" + line + "' nije cijeli broj.");
		}
		return true;
	}

	/**
	 * Metoda koja služi za izračun n faktorijela iz raspona [0,20]. Složenost
	 * funkcije je O(N).
	 * 
	 * @param n Vrijednost za koju izračunavamo faktorijele
	 * @return Vrijednost n faktorijela.
	 * @throws IllegalArgumentException za vrijednosti n-a veće od 20 i manje od 0
	 */
	public static long factorialCalcululator(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Unešeni broj mora biti pozitivan!");
		} else if (n > 20) {
			throw new IllegalArgumentException("Unešeni broj mora biti manji od 20!");
		}

		long factorial = 1;
		for (int j = 1; j <= n; j++) {
			factorial *= j;
		}
		return factorial;
	}

}
