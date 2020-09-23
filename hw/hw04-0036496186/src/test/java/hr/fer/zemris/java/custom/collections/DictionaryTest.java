package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class DictionaryTest {
	@Test
	public void constructorAndIsEmptyTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		assertEquals(true, dict.isEmpty());
		assertEquals(0, dict.size());
	}

	@Test
	public void dictionaryPutTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		assertEquals(false, dict.isEmpty());
		assertEquals(1, dict.size());
		dict.put(43, "maarko");
		assertEquals(false, dict.isEmpty());
		assertEquals(2, dict.size());
	}
	
	@Test
	public void dictionaryNullKeyNullValue() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		assertThrows(NullPointerException.class,()->dict.put(null, null));
	}
	
	@Test
	public void dictionaryNullKey() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		assertThrows(NullPointerException.class,()->dict.put(null, "tri"));
	}
	
	@Test
	public void dictionaryPutNullValue() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, null);
		assertEquals(false, dict.isEmpty());
		assertEquals(1, dict.size());
		assertEquals(null, dict.get(3));
	}

	@Test
	public void clearTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		dict.put(43, "maarko");
		assertEquals(2, dict.size());
		dict.clear();
		assertEquals(0, dict.size());
	}

	@Test
	public void dictionaryPutEgxistingKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		assertEquals(false, dict.isEmpty());
		assertEquals(1, dict.size());
		dict.put(3, "maarko2");
		assertEquals(false, dict.isEmpty());
		assertEquals(1, dict.size());
		assertEquals("maarko2", dict.get(3));
	}
	
	@Test
	public void dictionaryGetValueFromEgxistingKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		dict.put(31, "maarko1");
		dict.put(32, "maarko2");
		dict.put(33, "maarko3");
		assertEquals("maarko", dict.get(3));
	}
	
	@Test
	public void dictionaryGetValueFromNoneEgxistingKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		dict.put(31, "maarko1");
		dict.put(32, "maarko2");
		dict.put(33, "maarko3");
		assertThrows(NoSuchElementException.class,()-> dict.get(35));
	}
	
	@Test
	public void dictionaryGetValueFromNullKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		dict.put(3, "maarko");
		dict.put(31, "maarko1");
		dict.put(32, "maarko2");
		dict.put(33, "maarko3");
		assertThrows(NoSuchElementException.class,()-> dict.get(null));
	}
}
