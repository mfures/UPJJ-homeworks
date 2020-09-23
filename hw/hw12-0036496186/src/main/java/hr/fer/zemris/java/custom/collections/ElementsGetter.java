package hr.fer.zemris.java.custom.collections;

/**
 * Describes a simple interface used to walk trough elements in current
 * collection
 * 
 * @author Matej Fureš
 *
 */
public interface ElementsGetter {
	/**
	 * Implement this method to return true only if next element in collection is
	 * available.
	 * 
	 * @return true only if collection has more elements
	 */
	boolean hasNextElement();

	/**
	 * Implement this method to return next element is collection if any are
	 * available.
	 * 
	 * @return next available element in collection
	 */
	Object getNextElement();

	/**
	 * Method uses method process() of given Processor p on all remaining elements
	 * in the collection
	 * 
	 * @param p Processor which is used to process remaining elements in collection
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
