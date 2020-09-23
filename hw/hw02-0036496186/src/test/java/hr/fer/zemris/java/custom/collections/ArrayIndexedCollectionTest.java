package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void insertIntoMiddle() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(2);
		coll.add(3);
		coll.insert(5, 1);
		assertEquals(3, coll.size());
	}

	@Test
	public void inserIntoByReallocatingArray() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for (int i = 0; i < 16; i++) {
			coll.add(i);
		}
		assertEquals(16, coll.size());
		coll.insert(5, 5);
		assertEquals(17, coll.size());
	}

	@Test
	public void insertIntoEmpty() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.insert(5, 0);
		assertEquals(1, coll.size());
	}

	@Test
	public void insertIntoNegativeIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> coll.insert(5, -5));
		assertEquals(0, coll.size());
	}

	@Test
	public void insertIntoNull() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> coll.insert(null, -5));
		assertEquals(0, coll.size());
	}

	@Test
	public void insertIntoInvalidIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> coll.insert(5, 5000));
		assertEquals(0, coll.size());
	}

	@Test
	public void indexOfExistingElement() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertEquals(1, coll.indexOf(4));
	}

	@Test
	public void indexOfNonExistingElement() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertEquals(-1, coll.indexOf(5));
	}

	@Test
	public void removeInvalidIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertThrows(IndexOutOfBoundsException.class, () -> coll.remove(5));
		assertThrows(IndexOutOfBoundsException.class, () -> coll.remove(-5));
	}

	@Test
	public void removeValidIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		coll.remove(1);
		assertEquals(1, coll.size());
		coll.remove(0);
		assertEquals(0, coll.size());
	}

	@Test
	public void getInvalidIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertThrows(IndexOutOfBoundsException.class, () -> coll.get(5));
	}

	@Test
	public void getNegativeIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertThrows(IndexOutOfBoundsException.class, () -> coll.get(-5));
	}

	@Test
	public void getValidIndex() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(3);
		coll.add(4);
		assertEquals(3, coll.get(0));
		assertEquals(4, coll.get(1));
	}

	@Test
	public void clearNonEmptyCollection() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(1);
		coll.add(2);
		assertEquals(2, coll.size());
		coll.clear();
		assertEquals(0, coll.size());
	}

	@Test
	public void clearEmptyCollection() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.clear();
		assertEquals(0, coll.size());
	}

	@Test
	public void constructorWithCollectionWithReallocatedArrayAndCapacity() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		for (int i = 0; i < 17; i++) {
			coll2.add("V" + i);
		}
		ArrayIndexedCollection coll = new ArrayIndexedCollection(coll2, 10);
		assertEquals(17, coll.size());
		assertEquals(17, coll.toArray().length);
	}

	@Test
	public void constructorWithCollectionAndCapacity() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			coll2.add("V" + i);
		}
		ArrayIndexedCollection coll = new ArrayIndexedCollection(coll2, 10);
		assertEquals(5, coll.size());
		assertEquals(5, coll.toArray().length);
	}

	@Test
	public void addTwoObjects() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		coll2.add(2);
		coll2.add(2);
		assertEquals(2, coll2.size());
		Object[] arr = coll2.toArray();
		assertEquals(2, arr.length);
		assertEquals(2, coll2.get(0));
		assertEquals(2, coll2.get(1));
		for (int i = 0; i < 2; i++) {
			assertEquals(2, arr[i]);
		}
	}

	@Test
	public void addNull() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> coll2.add(null));
	}

	@Test
	public void constructorWithCollectionWithReallocatedArray() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		for (int i = 0; i < 17; i++) {
			coll2.add("V" + i);
		}
		ArrayIndexedCollection coll = new ArrayIndexedCollection(coll2);
		assertEquals(17, coll.size());
		assertEquals(17, coll.toArray().length);
	}

	@Test
	public void constructorWithNonEmptyCollection() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			coll2.add("V" + i);
		}
		ArrayIndexedCollection coll = new ArrayIndexedCollection(coll2);
		assertEquals(5, coll.size());
		assertEquals(5, coll.toArray().length);
	}

	@Test
	public void constructorWithEmptyCollection() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		ArrayIndexedCollection coll = new ArrayIndexedCollection(coll2);
		assertEquals(0, coll.size());
		assertEquals(0, coll.toArray().length);
	}

	@Test
	public void constructorWithCollectionAndNegativeNumber() {
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			coll2.add("V" + i);
		}
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(coll2, -54));
	}

	@Test
	public void defaultConstructorWithoutChangingArraySize() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertEquals(0, coll.size());
		assertEquals(0, coll.toArray().length);
		for (int i = 0; i < 5; i++) {
			coll.add("V" + i);
		}
		assertEquals(5, coll.size());
		assertEquals(5, coll.toArray().length);
	}

	@Test
	public void defaultConstructorWithChangingArraySizeByAddingElements() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertEquals(0, coll.size());
		assertEquals(0, coll.toArray().length);
		for (int i = 0; i < 17; i++) {
			coll.add("V" + i);
		}
		assertEquals(17, coll.size());
		assertEquals(17, coll.toArray().length);
	}

	@Test
	public void constructorWithNull() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}

	@Test
	public void constructorWithNegativeNumber() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-5));
	}

	@Test
	public void constructorWithZero() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}

	@Test
	public void ComplexTestWithDefaultConstructor() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertEquals(true, coll.isEmpty());
		assertEquals(0, coll.size());
		assertEquals(false, coll.remove("Vrijednost"));
		coll.add("Vrijednost0");
		assertEquals(false, coll.isEmpty());
		assertEquals(1, coll.size());
		for (int i = 1; i < ArrayIndexedCollection.DEFAULTARRAYSIZE; i++) {
			coll.add("Vrijednost" + i);
		}
		assertEquals(ArrayIndexedCollection.DEFAULTARRAYSIZE, coll.size());
		assertEquals(16, coll.toArray().length);
		for (int i = 0; i < ArrayIndexedCollection.DEFAULTARRAYSIZE; i++) {
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
	public void ComplexTestWithDifferentInitialCapacity() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
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
	public void forEachStringProcess() {
		ArrayIndexedCollection link = new ArrayIndexedCollection(), link2 = new ArrayIndexedCollection();
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
