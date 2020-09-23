package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Defines puzzle as used in assignment
 * 
 * @author Matej Fureš
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {
	/**
	 * Initial state
	 */
	private KonfiguracijaSlagalice s0;

	/**
	 * Constructor. s0 mustn't be null
	 * 
	 * @param s0 initial state
	 */
	public Slagalica(KonfiguracijaSlagalice s0) {
		Objects.requireNonNull(s0);

		this.s0 = s0;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		int[] data = t.getPolje();
		for (int i = 0; i <= 7; i++) {
			if (data[i] != i + 1) {
				return false;
			}
		}

		if (data[8] != 0) {
			return false;
		}

		return true;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();

		int posOfZero = t.indexOfSpace();

		switch (posOfZero) {
		case 4:
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(1, 4, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(3, 4, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(5, 4, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(7, 4, t.getPolje())), 1));
			return list;
		case 3:
		case 5:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(posOfZero - 3, posOfZero, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(posOfZero + 3, posOfZero, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(4, posOfZero, t.getPolje())), 1));
			return list;
		case 7:
		case 1:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(posOfZero - 1, posOfZero, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(posOfZero + 1, posOfZero, t.getPolje())), 1));
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(4, posOfZero, t.getPolje())), 1));
			return list;
		default:
			if (posOfZero == 0 || posOfZero == 2) {
				list.add(new Transition<KonfiguracijaSlagalice>(
						new KonfiguracijaSlagalice(swap(posOfZero + 3, posOfZero, t.getPolje())), 1));
			} else {
				list.add(new Transition<KonfiguracijaSlagalice>(
						new KonfiguracijaSlagalice(swap(posOfZero - 3, posOfZero, t.getPolje())), 1));
			}

			if (posOfZero == 0 || posOfZero == 6) {
				list.add(new Transition<KonfiguracijaSlagalice>(
						new KonfiguracijaSlagalice(swap(posOfZero + 1, posOfZero, t.getPolje())), 1));
			} else {
				list.add(new Transition<KonfiguracijaSlagalice>(
						new KonfiguracijaSlagalice(swap(posOfZero - 1, posOfZero, t.getPolje())), 1));
			}
		}

		return list;
	}

	/**
	 * Swaps positions in array
	 * 
	 * @param x   first position
	 * @param y   second position
	 * @param arr of numbers
	 * @return array
	 */
	private int[] swap(int x, int y, int[] arr) {
		int tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
		return arr;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return s0;
	}

}
