package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FactorialTest {

	@Test
	public void factorialOdNula() {
		try {
			long n = Factorial.factorialCalcululator(0);
			assertEquals(1, n);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void factorialOdJedan() {
		try {
			long n = Factorial.factorialCalcululator(1);
			assertEquals(1, n);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void factorialOdTri() {
		try {
			long n = Factorial.factorialCalcululator(3);
			assertEquals(6, n);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void factorialOdDvadeset() {
		try {
			long n = Factorial.factorialCalcululator(20);
			assertEquals(2432902008176640000L, n);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void factorialOdDvadesetJedan() {
		try {
			Factorial.factorialCalcululator(21);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni broj mora biti manji od 20!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void factorialOdPedeset() {
		try {
			Factorial.factorialCalcululator(50);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni broj mora biti manji od 20!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void factorialOdMinusJedan() {
		try {
			Factorial.factorialCalcululator(-1);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni broj mora biti pozitivan!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void factorialOdMinusSto() {
		try {
			Factorial.factorialCalcululator(-100);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni broj mora biti pozitivan!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}
}
