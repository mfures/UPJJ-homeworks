package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation of resizable array-backed collection of objects which extends
 * class Collection. Duplicate elements are allowed. Storage of null references
 * is not allowed.
 * 
 * @author Matej Fureš
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Number of elements actually stored in elements array
	 */
	private int size;

	/**
	 * An array of object references that stores added elements
	 */
	private Object[] elements;

	/**
	 * Default array size for elements array.
	 */
	public static final int DEFAULTARRAYSIZE = 16;

	/**
	 * Creates the collection that has element array with default length
	 */
	public ArrayIndexedCollection() {
		this(DEFAULTARRAYSIZE);
	}

	/**
	 * Creates the collection by adding all to it from collection other. Elemnts
	 * array will have length of max(other.size(), DEFAULTARRAYSIZE). Null value is
	 * not accepted.
	 * 
	 * @param other collection which elements are added to our collection
	 * @throws NullPointerException collection other is null
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULTARRAYSIZE);
	}

	/**
	 * Creates the collection that has element array where length equals
	 * initialCapacity. If initialCapacity i less than 1, an exception is thrown.
	 * 
	 * @param initialCapacity initial length of elements array
	 * @throws IllegalArgumentException if initialCapacity is <1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		isLessThanOne(initialCapacity);

		elements = new Object[initialCapacity];
	}

	/**
	 * Creates the collection by adding all to it from collection other. Elements
	 * array will have length of max(other.size(), initialCapacity). Null value is
	 * not accepted. If initialCapacity is less than 1, an exception is throw.
	 * 
	 * @param other           collection which elements are added to our collection
	 * @param initialCapacity initial length of elements array
	 * @throws IllegalArgumentException if initialCapacity is <1
	 * @throws NullPointerException     collection other is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		isLessThanOne(initialCapacity);
		Objects.requireNonNull(other);

		elements = new Object[Math.max(initialCapacity, other.size())];
		addAll(other);
	}

	/**
	 * Private method that throws and exception if n is < 1.
	 * 
	 * @param n number we check
	 * @throws IllegalArgumentException for n < 1
	 */
	private void isLessThanOne(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds element to the back of the elements array. If elements array is full,
	 * its elements are reallocated to a new array that has double length of current
	 * array. Average complexity is O(1), except when the underlying array in used
	 * collection is reallocated when complexity is O(n)
	 * 
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Returns the object that is stored in backing array at position index. Valid
	 * indexes are 0 to size-1. If index is invalid, the implementation throws an
	 * exception. Average complexity is O(1)
	 * 
	 * @param index of element we want to access
	 * @return element located at position index
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public Object get(int index) {
		isIndexOutOfBounds(index, 1);

		return elements[index];
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 */
	@Override
	public Object[] toArray() {
		Object[] tmpArray = new Object[size];

		for (int i = 0; i < size; i++) {
			tmpArray[i] = elements[i];
		}

		return tmpArray;
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * equals method.
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index an exception is
	 * thrown.
	 * 
	 * @param index position of element we wish to remove
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public void remove(int index) {
		isIndexOutOfBounds(index, 1);

		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}

		size--;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it(the one with loves index in
	 * the array).
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);

		if (index == -1) {
			return false;
		}

		remove(index);
		return true;
	}

	/**
	 * Removes all elements from the collection. The allocated array is left at
	 * current capacity
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * Returns the number of currently stored elements in the collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. Argument can be null. The
	 * equality is determined by using the equals method. Average complexity is
	 * O(n).
	 * 
	 * @param value element we are looking for
	 * @return index of the first occurrence of the given value or -1 if not found
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * The legal positions are 0 to size (both are included). If position is
	 * invalid, an appropriate exception is thrown. If backing array is full, array
	 * size is doubled. Does not accept null value. Average complexity is O(n)
	 * 
	 * @param value    element to be inserted
	 * @param position position to insert on
	 * @throws IndexOutOfBoundsException if n<0||n>size
	 * @throws NullPointerException      of value == null
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		isIndexOutOfBounds(position, 0);
		doubleElementsLengthIfFull();

		elements[size] = value;
		size++;

		for (int i = size - 2; i >= position; i--) {
			elements[i + 1] = elements[i];
		}

		elements[position] = value;
	}

	/**
	 * Checks if backing array is full, and copies it into a new array with double
	 * length if it is.
	 */
	private void doubleElementsLengthIfFull() {
		if (size() == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}
	}

	/**
	 * Private method that throws and exception if n is < 1 and n >
	 * size()-subtractFromSize.
	 * 
	 * @param n                number that is checked
	 * @param subtractFromSize number that is subtracted from size
	 * @throws IndexOutOfBoundsException if n is < 1 and n > size()-subtractFromSize
	 */
	private void isIndexOutOfBounds(int n, int subtractFromSize) {
		if (n < 0 || n > size - subtractFromSize) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * Elements are processed in accordance with the position in elements array
	 * 
	 * @throws NullPointerException if processor = null
	 */
	@Override
	public void forEach(Processor processor) {
		Objects.requireNonNull(processor);

		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
}
