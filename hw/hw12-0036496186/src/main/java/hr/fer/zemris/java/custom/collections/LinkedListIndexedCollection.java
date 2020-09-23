package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementation of linked list-backed collection of objects denoted as
 * LinkedListIndexedCollection which extends class Collection. Object values are
 * stored id nodes. Duplicate elements are allowed (each of those element will
 * be held in different list node). Storage of null references is not allowed.
 * 
 * @author Matej Fureš
 *
 */
public class LinkedListIndexedCollection implements List {
	/**
	 * Define private static class ListNode with pointers to previous and next list
	 * node and additional reference for value storage.
	 * 
	 * @author Matej Fureš
	 *
	 */
	private static class ListNode {

		/**
		 * Next node in the list
		 */
		private ListNode nextNode;

		/**
		 * Previous node in the list
		 */
		private ListNode previousNode;

		/**
		 * Reference for value storage
		 */
		private Object value;

		/**
		 * Creates new ListNode which has its value set to value, and everything else to
		 * null.
		 * 
		 * @param value that node is set on
		 */
		private ListNode(Object value) {
			this.value = value; 
		} 
	}

	/**
	 * Current size of collection (number of elements actually stored).
	 */
	private int size;

	/**
	 * Reference to the first node of the linked list
	 */
	private ListNode first;

	/**
	 * Reference to the last node of the linked list
	 */
	private ListNode last;

	/**
	 * Tracks number of structural modifications made to collection
	 */
	private long modificationCount;

	/**
	 * Defines an ElementsGetter implementation that is suited for working with
	 * LinkedListInedxedCollection
	 * 
	 * @author Matej Fureš
	 *
	 */
	private static class LinkedListElementsGetter implements ElementsGetter {
		/**
		 * Next node to return to the user
		 */
		private ListNode next;

		/**
		 * Reference to collection that is cycled
		 */
		private LinkedListIndexedCollection collection;

		/**
		 * Initial number of modifications made to collection
		 */
		private long savedModificationCount;

		/**
		 * Creates an ElementsGetter object using LinkedListIndexedCollection first
		 * element
		 * 
		 * @param first first element in collection to be cycled
		 */
		private LinkedListElementsGetter(LinkedListIndexedCollection collection) {
			this.collection = collection;
			next = collection.first;
			savedModificationCount = collection.modificationCount;
		}

		/**
		 * This method returns false only if there are no more elements to get. If
		 * collection has changed after ElementsGetters initialization this method also
		 * throws an exception.
		 * 
		 * @return true only if there are more elements in collection
		 * @throws ConcurrentModificationException if collection has been changed after
		 *                                         initialization of ElementsGetter
		 */
		@Override
		public boolean hasNextElement() {
			hasCollectionChanged();

			return next != null;
		}

		/**
		 * This method returns next element in collection. If there are none, exception
		 * is thrown. If collection has changed after ElementsGetters initialization
		 * this method also throws an exception.
		 * 
		 * @return next element in collection
		 * @throws NoSuchElementException          if there are no more elements
		 * @throws ConcurrentModificationException if collection has been changed after
		 *                                         initialization of ElementsGetter
		 */
		@Override
		public Object getNextElement() {
			hasCollectionChanged();

			if (next == null) {
				throw new NoSuchElementException();
			}

			ListNode node = next;
			next = next.nextNode;

			return node.value;
		}

		/**
		 * Throws ConcurrentModificationException if collection has been changed after
		 * ElementsGetters initialization.
		 * 
		 * @throws ConcurrentModificationException if collection has been changed after
		 *                                         initialization of ElementsGetter
		 */
		private void hasCollectionChanged() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
		}

	}

	/**
	 * Makes an empty collection
	 */
	public LinkedListIndexedCollection() {

	}

	/**
	 * Constructor has a single parameter: reference to some other Collection whose
	 * elements are copied into this newly constructed collection.
	 * 
	 * @param other Collection whose elements are copied into this new collection
	 * @throws NullPointerException if other is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(Objects.requireNonNull(other));
	}

	/**
	 * Adds the given object into this collection at the end of collection; newly
	 * added element becomes the element at the biggest index. Throws an exception
	 * if element is null. If this method doesn't throw an exception
	 * modificationCount is increased.
	 * 
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 */
	@Override
	public Object[] toArray() {
		Object[] tmpArray = new Object[size];
		ListNode node = first;
		for (int i = 0; i < size; i++) {
			tmpArray[i] = node.value;
			node = node.nextNode;
		}
		return tmpArray;
	}

	/**
	 * Returns the number of currently stored elements in the collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Private method which goes to the index position inside the list, and returns
	 * element on that position.
	 * 
	 * @param index position of element in list
	 * @return node element on position index inside of list
	 */
	private ListNode getNodeAt(int index) {
		ListNode node = null;

		if (index <= (size - 1) / 2) {
			int i = 0;
			node = first;
			while (i != index) {
				node = node.nextNode;
				i++;
			}
		} else {
			int i = size - 1;
			node = last;
			while (i != index) {
				node = node.previousNode;
				i--;
			}
		}

		return node;
	}

	/**
	 * Returns the object that is stored in linked list at position index. Valid
	 * indexes are 0 to size-1. If index is invalid, the implementation throws an
	 * exception.
	 * 
	 * @param index position of element we want to access
	 * @return element on position index
	 * @throws IndexOutOfBoundsException if index is<0 and >size-1
	 */
	public Object get(int index) {
		isIndexOutOfBounds(index, 1);

		return getNodeAt(index).value;
	}

	/**
	 * Removes all elements from the collection. This method increases modificationCount.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * linked-list. Elements starting from this position are shifted one position.
	 * The legal positions are 0 to size. If position is invalid, an appropriate
	 * exception is thrown. If element is null, an exception is thrown. Average
	 * complexity O(n/2). If this method doesn't throw an exception
	 * modificationCount is increased.
	 * 
	 * @param value    value to be added to the collection
	 * @param position position at which the element is added
	 * @throws IndexOutOfBoundsException if position is invalid
	 * @throws NullPointerException      if value is null
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		isIndexOutOfBounds(position, 0);
		
		modificationCount++;

		if (size == 0) {
			first = new ListNode(value);
			updateLastNode(first);
			return;
		}

		if (size == position) {
			last.nextNode = new ListNode(value);
			updateLastNode(last.nextNode);
			return;
		}

		ListNode newNode = new ListNode(value);
		ListNode tmpNode = getNodeAt(position);

		newNode.nextNode = tmpNode;
		newNode.previousNode = tmpNode.previousNode;
		newNode.nextNode.previousNode = newNode;
		if (position == 0) {
			first = newNode;
		} else {
			newNode.previousNode.nextNode = newNode;
		}

		size++;
	}

	/**
	 * Method adds node to the back of the list and increases size
	 */
	private void updateLastNode(ListNode node) {
		node.previousNode = last;
		last = node;
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. null is valid argument. The
	 * equality is determined by using the equals method. Average complexity of this
	 * is O(n/2)
	 * 
	 * @param value value of element we are looking for
	 * @return index index of the first occurrence of the given value or -1 if not
	 *         found
	 */
	public int indexOf(Object value) {
		ListNode tmp = first;
		int i = 0;
		while (i != size) {
			if (tmp.value.equals(value)) {
				return i;
			}

			tmp = tmp.nextNode;
			i++;
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index throws an
	 * exception. If this method doesn't throw an exception modificationCount is
	 * increased.
	 * 
	 * 
	 * @param index position of element to be deleted
	 * @throws IndexOutOfBoundsException if index is <0 ||>size-1
	 */
	public void remove(int index) {
		isIndexOutOfBounds(index, 1);

		modificationCount++;

		if (size == 1) {
			first = null;
			last = null;
			size = 0;
			return;
		}

		if (index == 0) {
			first = first.nextNode;
			first.previousNode = null;
			size--;
			return;
		}

		if (index == size - 1) {
			last = last.previousNode;
			last.nextNode = null;
			size--;
			return;
		}

		ListNode node = getNodeAt(index);

		node.nextNode.previousNode = node.previousNode;
		node.previousNode.nextNode = node.nextNode;

		size--;
	}

	/**
	 * Removes first occurrence of element value from collection. Element that were
	 * previously at bigger position than removed element go from index+1 to
	 * location index, etc. If this method doesn't return false an exception
	 * modificationCount is increased.
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
	 * Returns true only if the collection contains given value, as determined by
	 * equals method.
	 */
	@Override
	public boolean contains(Object value) {
		return -1 != indexOf(value);
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

	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
}
