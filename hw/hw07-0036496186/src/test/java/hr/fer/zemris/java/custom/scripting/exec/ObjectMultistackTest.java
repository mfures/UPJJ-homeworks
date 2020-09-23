package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {
	@Test
	public void pushTest() {
		ObjectMultistack ms = new ObjectMultistack();
		assertThrows(NullPointerException.class, () -> ms.push(null, null));
		assertThrows(NullPointerException.class, () -> ms.push(null, new ValueWrapper(3)));

		ms.push("a", null);
		ms.push("a", new ValueWrapper(1));
		ms.push("b", null);

		assertFalse(ms.isEmpty("a"));
		assertFalse(ms.isEmpty("b"));
	}

	@Test
	public void peekTest() {
		ObjectMultistack ms = new ObjectMultistack();

		ms.push("a", null);
		assertNull(ms.peek("a"));

		ms.push("a", new ValueWrapper(1));
		assertEquals(1, ms.peek("a").getValue());

		ms.pop("a");
		assertNull(ms.peek("a"));

		ms.push("b", null);
		assertNull(ms.peek("b"));

		assertThrows(NoSuchElementException.class, () -> ms.peek("c"));
	}

	@Test
	public void popTest() {
		ObjectMultistack ms = new ObjectMultistack();
		assertThrows(NoSuchElementException.class, () -> ms.pop("c"));
		ms.push("a", null);
		ms.push("a", new ValueWrapper(1));		
		assertEquals(1, ms.pop("a").getValue());
		ms.push("a", new ValueWrapper(2));
		assertEquals(2, ms.pop("a").getValue());
		assertEquals(null, ms.pop("a"));
		assertThrows(NoSuchElementException.class, () -> ms.pop("a"));
	}
	
	@Test
	public void isEmpty() {
		ObjectMultistack ms = new ObjectMultistack();
		assertTrue(ms.isEmpty(null));
		assertTrue(ms.isEmpty("a"));
		ms.push("a", null);
		assertFalse(ms.isEmpty("a"));
	}
}
