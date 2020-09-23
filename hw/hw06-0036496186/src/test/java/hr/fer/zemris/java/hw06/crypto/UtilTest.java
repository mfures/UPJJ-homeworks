package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTest {
	@Test
	public void b2hNull() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}

	@Test
	public void b2hZeroLengthArray() {
		String hex = Util.bytetohex(new byte[0]);
		assertEquals(0, hex.length());
	}

	@Test
	public void b2hTest() {
		String hex = Util.bytetohex(new byte[] { 1, -82, 34 });
		assertEquals("01ae22", hex);
	}

	@Test
	public void h2bNull() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}

	@Test
	public void h2bIllegalChar() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("2G"));
	}

	@Test
	public void h2bIllegalStringLength() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("2F3"));
	}

	@Test
	public void h2bEmptyString() {
		byte[] bar = Util.hextobyte("");
		assertEquals(0, bar.length);
	}

	@Test
	public void h2bString() {
		byte[] bar = Util.hextobyte("01aE22");

		assertEquals(1, bar[0]);
		assertEquals(-82, bar[1]);
		assertEquals(34, bar[2]);
	}

}
