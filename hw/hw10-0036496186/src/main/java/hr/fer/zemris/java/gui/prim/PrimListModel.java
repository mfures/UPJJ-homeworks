package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of ListModel used for providing prime numbers
 * 
 * @author matfures
 *
 */
public class PrimListModel implements ListModel<String> {
	/**
	 * List containing all prime numbers calculated so far
	 */
	List<String> primeNumbers;

	/**
	 * List containing all listeners
	 */
	List<ListDataListener> listListeners;

	/**
	 * Constructor
	 */
	public PrimListModel() {
		listListeners = new ArrayList<>();
		primeNumbers = new ArrayList<>();
		primeNumbers.add("1");
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public String getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l);

		listListeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listListeners.remove(l);
	}

	/**
	 * Calculates next prime number
	 */
	public void next() {
		int last = Integer.parseInt(primeNumbers.get(primeNumbers.size() - 1));
		boolean primeFlag;

		for (int i = last + 1; i < Integer.MAX_VALUE; i++) {
			primeFlag = true;
			for (int j = 1; j < primeNumbers.size() - 1; j++) {
				if (i % Integer.parseInt(primeNumbers.get(j)) == 0) {
					primeFlag = false;
					break;
				}
			}

			if (primeFlag) {
				last = i;
				break;
			}
		}
		primeNumbers.add(Integer.toString(last));
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primeNumbers.size() - 1,
				primeNumbers.size() - 1);
		listListeners.forEach(x -> x.intervalAdded(event));
	}

}
