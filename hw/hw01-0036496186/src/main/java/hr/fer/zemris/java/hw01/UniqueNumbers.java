package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program služi za dodavanje brojeva unešenih preko tipkovnice u sortirano
 * binarno stablo. Program prikuplja cijele brojeve dok se ne unese poruka
 * "kraj" nakon čega se brojevi ispisuju sortirani.Program se pokreće bez
 * argumenata. Priliko unosa nevažeće linije program ispisuje poruku i nastavlja
 * s radom. Za već postojojeće brojeve program ne dodaje broj u stablo, već
 * ispisuje poruku.
 * 
 * @author Matej Fureš
 * 
 */
public class UniqueNumbers {

	public static class TreeNode {
		public TreeNode left, right;
		public int value;
	}

	/**
	 * Metoda main čita podatke sa tipkovnice pa te podatke prosljeđuje metodi
	 * handleLine na obradu u skladu sa opisom rada programa. Po završetku unosa
	 * ispisuje sortirane brojeve ulazno i silazno.
	 * 
	 * @param args Nema ulaznih podataka
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Ovaj program pokreće se bez argumenata!");
			return;
		}

		Scanner scan = new Scanner(System.in);
		TreeNode glava = null;
		System.out.print("Unesite broj > ");
		String line = scan.nextLine();
		while (line.equals("kraj") == false) {
			glava = handleLine(line, glava);
			System.out.print("Unesite broj > ");
			line = scan.nextLine();
		}

		scan.close();

		System.out.println("Ispis od najmanjeg:" + minToMax(glava));
		System.out.println("Ispis od najvećeg:" + maxToMin(glava));

	}

	/**
	 * Metoda obrađuje ulazni string, koji ako je cijeli broj koji se ne nalazi u
	 * stablu šalje na dodavanje u stablo, inače ispisuje poruku.
	 * 
	 * @param line  Ulazna linija
	 * @param glava Korijen stabla u koje dodajemo novi čvor
	 * @return Aktualan korijen stabla
	 */
	private static TreeNode handleLine(String line, TreeNode glava) {
		try {
			int n = Integer.parseInt(line);
			if (containsValue(glava, n)) {
				System.out.println("Broj već postoji. Preskačem.");
			} else {
				glava = addNode(glava, (int) n);
				System.out.println("Dodano.");
			}
		} catch (NumberFormatException e) {
			System.out.println("'" + line + "' nije cijeli broj.");
		}
		return glava;
	}

	/**
	 * Metoda rekurzivno prebrojava čvorove u stablu.
	 * 
	 * @param node korijen stabla ili podstabla kod rekurzivnih poziva
	 * @return broj djece u stablu
	 */
	public static int treeSize(TreeNode node) {
		if (node == null)
			return 0;

		return treeSize(node.left) + 1 + treeSize(node.right);
	}

	/**
	 * Metoda provjerava postoji li čvor sa zadanom vrijednosti u stablu.
	 * 
	 * @param node  Korijen stabla
	 * @param value Vrijednost koju tražimo u stablu
	 * @return true ako vrijednost postoji u stablu, false inače
	 */
	public static boolean containsValue(TreeNode node, int value) {
		if (node == null) {
			return false;
		}

		if (node.value == value) {
			return true;
		}

		if (node.value > value) {
			return containsValue(node.left, value);
		} else {
			return containsValue(node.right, value);
		}
	}

	/**
	 * Metoda za dodavanje čvorova u binarno stablo. Ne dodaje duplikate.
	 * 
	 * @param node     Korijen stabla
	 * @param newValue Vrijednost koju želimo dodati
	 * @return Aktualan korijen stabla
	 */
	public static TreeNode addNode(TreeNode node, int newValue) {
		if (node == null) {
			TreeNode newNode = new TreeNode();
			newNode.value = newValue;
			return newNode;
		}

		if (node.value != newValue) {
			if (node.value > newValue) {
				if (node.left != null) {
					addNode(node.left, newValue);
				} else {
					TreeNode newNode = new TreeNode();
					newNode.value = newValue;
					node.left = newNode;
				}
			} else {
				if (node.right != null) {
					addNode(node.right, newValue);
				} else {
					TreeNode newNode = new TreeNode();
					newNode.value = newValue;
					node.right = newNode;
				}
			}
		}
		return node;
	}

	/**
	 * Metoda koja vraća String sortiranih vrijednost stabla sortirano od manjih
	 * prema većim.
	 * 
	 * @param glava Korijen stabla
	 * @return sortirani brojevi
	 */
	public static String minToMax(TreeNode glava) {
		if (glava == null)
			return "";

		return minToMax(glava.left) + " " + glava.value + minToMax(glava.right);
	}

	/**
	 * Metoda koja vraća String sortiranih vrijednost stabla sortirano od većih
	 * prema manjim.
	 * 
	 * @param glava Korijen stabla
	 * @return sortirani brojevi
	 */
	public static String maxToMin(TreeNode glava) {
		if (glava == null)
			return "";

		return maxToMin(glava.right) + " " + glava.value + maxToMin(glava.left);
	}
}
