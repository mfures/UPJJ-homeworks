package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RectangleTest {

	@Test
	public void površinaJedanJedan() {
		try {
			Double d = Rectangle.calculateArea(1.0, 1.0);
			assertEquals(1, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void površinaPetTri() {
		try {
			Double d = Rectangle.calculateArea(5.0, 3.0);
			assertEquals(15, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void površinaRazlomak() {
		try {
			Double d = Rectangle.calculateArea(5.2, 3.2);
			assertEquals(16.64, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void površinaNegativno() {
		try {
			Rectangle.calculateArea(-5.2, 3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void površinaNegativnoDva() {
		try {
			Rectangle.calculateArea(5.2, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void površinaNegativnoTri() {
		try {
			Rectangle.calculateArea(-5.2, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void površinaNula() {
		try {
			Rectangle.calculateArea(0.0, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void opsegJedanJedan() {
		try {
			Double d = Rectangle.calculatePerimeter(1.0, 1.0);
			assertEquals(4, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void opsegPetTri() {
		try {
			Double d = Rectangle.calculatePerimeter(5.0, 3.0);
			assertEquals(16, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void opsegRazlomak() {
		try {
			Double d = Rectangle.calculatePerimeter(5.2, 3.2);
			assertEquals(16.8, d);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}

	@Test
	public void perimeterNegativno() {
		try {
			Rectangle.calculatePerimeter(-5.2, 3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void perimeterNegativnoDva() {
		try {
			Rectangle.calculatePerimeter(5.2, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void perimeterNegativnoTri() {
		try {
			Rectangle.calculatePerimeter(-5.2, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}

	@Test
	public void perimeterNula() {
		try {
			Rectangle.calculatePerimeter(0.0, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}
	
	@Test
	public void solveDvaOsam() {
		try {
			String s = Rectangle.solve(2.0, 8.0);
			assertEquals("Pravokutnik širine 2.0 i visine 8.0 ima površinu 16.0 te opseg 20.0.", s);
		} catch (IllegalArgumentException e) {
			fail("Nisam očekivao iznimku.");
		}
	}
	
	@Test
	public void solveNeispravno() {
		try {
			Rectangle.solve(-5.2, -3.2);
			fail("Očekivao sam iznimku.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unešeni brojevi moraju biti pozitivni!", e.getMessage());
		} catch (Exception e) {
			fail("Očekivao sam drugu iznimku.");
		}
	}
}
