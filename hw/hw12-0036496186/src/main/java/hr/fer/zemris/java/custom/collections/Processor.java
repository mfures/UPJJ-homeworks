package hr.fer.zemris.java.custom.collections;

/**
 * Model of an object capable of performing some operation on the passed object
 * 
 * @author Matej Fureš
 *
 */
public interface Processor {
	/**
	 * Method for performing some operation on the passed object
	 * 
	 * @param value Object on which operation is performed
	 */
	void process(Object value);
}
