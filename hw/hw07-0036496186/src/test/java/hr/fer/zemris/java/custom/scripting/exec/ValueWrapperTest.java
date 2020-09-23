package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {
	@Test
	public void testAdd() {
		ValueWrapper intv = new ValueWrapper(null);

		intv.add(1);
		assertEquals(1, intv.getValue());

		intv.add(2);
		assertEquals(3, intv.getValue());

		intv.add("3");
		assertEquals(6, intv.getValue());

		intv.add(3.2);
		assertEquals(9.2, intv.getValue());

		intv.add(null);
		assertEquals(9.2, intv.getValue());
	}

	@Test
	public void testSub() {
		ValueWrapper intv = new ValueWrapper(null);

		intv.subtract(1);
		assertEquals(-1, intv.getValue());

		intv.subtract(2);
		assertEquals(-3, intv.getValue());

		intv.subtract("3");
		assertEquals(-6, intv.getValue());

		intv.subtract(3.2);
		assertEquals(-9.2, intv.getValue());

		intv.subtract(null);
		assertEquals(-9.2, intv.getValue());
	}

	@Test
	public void testMull() {
		ValueWrapper intv = new ValueWrapper(null);

		intv.multiply(1);
		assertEquals(0, intv.getValue());

		intv.setValue(1);

		intv.multiply(2);
		assertEquals(2, intv.getValue());

		intv.multiply("3");
		assertEquals(6, intv.getValue());

		intv.multiply(3.0);
		assertEquals(18.0, intv.getValue());

		intv.multiply(null);
		assertEquals(0.0, intv.getValue());
	}

	@Test
	public void testDiv() {
		ValueWrapper intv = new ValueWrapper(null);

		intv.divide(1);
		assertEquals(0, intv.getValue());

		intv.setValue(12);

		intv.divide(2);
		assertEquals(6, intv.getValue());

		intv.divide("3");
		assertEquals(2, intv.getValue());

		intv.divide(2.0);
		assertEquals(1.0, intv.getValue());

		intv.divide(null);
		assertEquals(Double.POSITIVE_INFINITY, intv.getValue());
	}

	@Test
	public void testComp() {
		ValueWrapper intv = new ValueWrapper(null);

		assertTrue(intv.numCompare(1) < 0);
		assertTrue(intv.numCompare(null) == 0);
		assertTrue(intv.numCompare(-1) > 0);
		assertTrue(intv.numCompare("1") < 0);
		assertTrue(intv.numCompare(1.0) < 0);
	}

	@Test
	public void example() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(0, v1.getValue());
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertEquals(13.0, v3.getValue());
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertEquals(13, v5.getValue());
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue())); // throws RuntimeException
		
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () ->vv1.add(Integer.valueOf(5))); // ==> throws, since current value is boolean
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () ->vv2.add(Boolean.valueOf(true))); // ==> throws, since the argument value is boolean
	}
}
