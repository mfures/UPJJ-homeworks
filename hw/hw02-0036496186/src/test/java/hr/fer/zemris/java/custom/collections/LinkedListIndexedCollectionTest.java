package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void removeFromBeggining() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(4);
		coll.add(3);
		coll.remove(0);
		assertEquals(1, coll.size());
		assertEquals(3, coll.get(0));
	}

	@Test
	public void removeFromBack() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.add(4);
		coll.remove(1);
		assertEquals(1, coll.size());
		assertEquals(3, coll.get(0));
	}

	@Test
	public void removeFromCollectionThatHasOnlyOneElement() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.remove(0);
		assertEquals(0, coll.size());
	}

	@Test
	public void removeFromEmptyList() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> coll.remove(0));
	}

	@Test
	public void removeInvalidIndex() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.add(4);

		assertThrows(IndexOutOfBoundsException.class, () -> coll.remove(5));
		assertThrows(IndexOutOfBoundsException.class, () -> coll.remove(-5));
	}

	@Test
	public void removeValidIndexFromMiddleOfCollection() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.add(4);
		coll.add(4);
		coll.add(4);
		coll.remove(1);
		assertEquals(3, coll.size());
		coll.remove(1);
		assertEquals(2, coll.size());
	}

	@Test
	public void indexOfEgsistingEleme() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertEquals(1, coll.indexOf(4));
	}

	@Test
	public void indexOfNonEgsistingElement() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertEquals(-1, coll.indexOf(5));
	}

	@Test
	public void insertIntoMiddle() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.add(1);
		link.add(2);
		assertEquals(1, link.get(0));
		assertEquals(2, link.get(1));
		link.insert("v0", 1);
		assertEquals("v0", link.get(1));
		link.insert("v1", 2);
		assertEquals(2, link.get(3));
		assertEquals("v1", link.get(2));
		assertEquals("v0", link.get(1));
		assertEquals(1, link.get(0));
	}

	@Test
	public void insertOnEnd() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.add(1);
		assertEquals(1, link.get(0));
		link.insert("v0", 1);
		assertEquals("v0", link.get(1));
		link.insert("v1", 2);
		assertEquals("v1", link.get(2));
		assertEquals("v0", link.get(1));
		assertEquals(1, link.get(0));
	}

	@Test
	public void insertOnBegging() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.add(1);
		assertEquals(1, link.get(0));
		link.insert("v0", 0);
		assertEquals("v0", link.get(0));
		link.insert("v1", 0);
		assertEquals("v1", link.get(0));
		assertEquals("v0", link.get(1));
		assertEquals(1, link.get(2));
	}

	@Test
	public void insertEmpty() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.insert("v0", 0);
		assertEquals(1, link.size());
		link.add("s");
		assertEquals(2, link.size());
	}

	@Test
	public void insertNull() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> link.insert(null, 0));
	}

	@Test
	public void insertNullNegative() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> link.insert(null, -5));
	}

	@Test
	public void inserIntoWithNegativeIndex() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> link.insert(3, -5));
	}

	@Test
	public void insertIntoInvalidIndex() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> link.insert(3, 5));
	}

	@Test
	public void clearNotEmpty() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.add(2);
		link.add(3);
		link.clear();
		assertEquals(0, link.size());
	}

	@Test
	public void clearEmpty() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		link.clear();
		assertEquals(0, link.size());
	}

	@Test
	public void getInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> (new LinkedListIndexedCollection()).get(1));
	}

	@Test
	public void getNegativeIndexFromCollection() {
		assertThrows(IndexOutOfBoundsException.class, () -> (new LinkedListIndexedCollection()).get(-1));
	}

	@Test
	public void getFromEmptyCollection() {
		assertThrows(IndexOutOfBoundsException.class, () -> (new LinkedListIndexedCollection()).get(0));
	}

	@Test
	public void addNull() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertEquals(0, link.size());
		assertThrows(NullPointerException.class, () -> link.add(null));
	}

	@Test
	public void constructorWithNull() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}

	@Test
	public void linkedListConstructorAddAndGet() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection();
		assertEquals(0, link.size());
		link.add("1");
		assertEquals("1", link.get(0));
		assertEquals(1, link.size());
		link.add("2");
		assertEquals("1", link.get(0));
		assertEquals("2", link.get(1));
		assertEquals(2, link.size());
		link.add("3");
		assertEquals("1", link.get(0));
		assertEquals("2", link.get(1));
		assertEquals("3", link.get(2));
		assertEquals(3, link.size());
		link.add("4");
		assertEquals("1", link.get(0));
		assertEquals("2", link.get(1));
		assertEquals("3", link.get(2));
		assertEquals("4", link.get(3));
		assertEquals(4, link.size());
	}

	@Test
	public void linkedListConstructorFromLinkedListIndexedCollection() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(2);
		coll.add(3);
		LinkedListIndexedCollection link = new LinkedListIndexedCollection(coll);
		assertEquals(2, link.toArray().length);
		assertEquals(2, link.size());
	}

	@Test
	public void linkedListConstructorFromArrayIndexCollection() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(2);
		coll.add(3);
		LinkedListIndexedCollection link = new LinkedListIndexedCollection(coll);
		assertEquals(2, link.toArray().length);
		assertEquals(2, link.size());
	}

	@Test
	public void complexTestWithSixteenElements() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		assertEquals(true, coll.isEmpty());
		assertEquals(0, coll.size());
		assertEquals(false, coll.remove("Vrijednost"));
		coll.add("Vrijednost0");
		assertEquals(false, coll.isEmpty());
		assertEquals(1, coll.size());
		for (int i = 1; i < 16; i++) {
			coll.add("Vrijednost" + i);
		}
		assertEquals(16, coll.size());
		assertEquals(16, coll.toArray().length);
		for (int i = 0; i < 16; i++) {
			assertEquals("Vrijednost" + i, coll.get(i));
		}
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + i, arr[i]);
			}
		}
		assertEquals(15, coll.indexOf("Vrijednost15"));
		assertEquals(0, coll.indexOf("Vrijednost0"));
		assertEquals(-1, coll.indexOf("Vrijednost-"));
		coll.add("Vrijednost16");
		assertEquals(17, coll.size());
		for (int i = 17; i < 70; i++) {
			coll.insert("Vrijednost" + i, i);
		}
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + i, arr[i]);
			}
		}
		coll.remove(0);
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + (i + 1), arr[i]);
			}
		}
		coll.remove("Vrijednost1");
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + (i + 2), arr[i]);
			}
		}
		coll.clear();
		{
			Object[] arr = coll.toArray();
			assertEquals(0, arr.length);
		}
		assertEquals(0, coll.size());
	}

	@Test
	public void complexTestStartingWithTwelveElements() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		assertEquals(true, coll.isEmpty());
		assertEquals(0, coll.size());
		assertEquals(false, coll.remove("Vrijednost"));
		coll.add("Vrijednost0");
		assertEquals(false, coll.isEmpty());
		assertEquals(1, coll.size());
		for (int i = 1; i < 12; i++) {
			coll.add("Vrijednost" + i);
		}
		assertEquals(12, coll.size());
		assertEquals(12, coll.toArray().length);
		for (int i = 0; i < 12; i++) {
			assertEquals("Vrijednost" + i, coll.get(i));
		}
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + i, arr[i]);
			}
		}
		assertEquals(11, coll.indexOf("Vrijednost11"));
		assertEquals(0, coll.indexOf("Vrijednost0"));
		assertEquals(-1, coll.indexOf("Vrijednost-"));
		coll.add("Vrijednost12");
		assertEquals(13, coll.size());
		for (int i = 13; i < 70; i++) {
			coll.insert("Vrijednost" + i, i);
		}
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + i, arr[i]);
			}
		}
		coll.remove(0);
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + (i + 1), arr[i]);
			}
		}
		coll.remove("Vrijednost1");
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + (i + 2), arr[i]);
			}
		}
		assertEquals(false, coll.contains("Vrijednost0"));
		coll.insert("Vrijednost0", 0);
		assertEquals(true, coll.contains("Vrijednost0"));
		coll.insert("Vrijednost1", 1);
		{
			Object[] arr = coll.toArray();
			for (int i = 0; i < arr.length; i++) {
				assertEquals("Vrijednost" + i, arr[i]);
			}
		}

		assertThrows(NullPointerException.class, () -> coll.forEach(null));
		coll.clear();
		{
			Object[] arr = coll.toArray();
			assertEquals(0, arr.length);
		}
		assertEquals(0, coll.size());
		coll.insert("bok", 0);
		assertEquals(1, coll.size());

	}

	@Test
	public void testFromHomework() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		assertEquals(true, col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		assertEquals("San Francisco", col.get(1)); // writes: "San Francisco"
		assertEquals(2, col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		// This is local class representing a Processor which writes objects to
		// System.out
		class P extends Processor {
			public void process(Object o) {
				// System.out.println(o);
			}
		}
		;
		// System.out.println("col elements:");
		col.forEach(new P());
		// System.out.println("col elements again:");
		// System.out.println(Arrays.toString(col.toArray()));
		// System.out.println("col2 elements:");
		col2.forEach(new P());
		// System.out.println("col2 elements again:");
		// System.out.println(Arrays.toString(col2.toArray()));
		assertEquals(true, col.contains(col2.get(1))); // true
		assertEquals(true, col2.contains(col.get(1))); // true
		col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0).
	}

	@Test
	public void forEachStringProcess() {
		LinkedListIndexedCollection link = new LinkedListIndexedCollection(), link2 = new LinkedListIndexedCollection();
		link.add("1");
		link.add("2");

		class P extends Processor {
			public void process(Object o) {
				link2.add(o);
			}
		}
		;
		link.forEach(new P());
		assertEquals("1", link2.get(0));
		assertEquals("2", link2.get(1));

	}
}
