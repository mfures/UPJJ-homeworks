package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2DTest {
	@Test
	public void constructorAndGettersTest() {
		Vector2D v = new Vector2D(0, 1);
		assertEquals(0, v.getX());
		assertEquals(1, v.getY());
	}

	@Test
	public void translateNull() {
		Vector2D v = new Vector2D(0, 1);
		assertThrows(NullPointerException.class, () -> v.translate(null));
	}

	@Test
	public void translatedNull() {
		Vector2D v = new Vector2D(0, 1);
		assertThrows(NullPointerException.class, () -> v.translated(null));
	}

	@Test
	public void translateTest() {
		Vector2D v = new Vector2D(1, 1);
		assertEquals(1, v.getX());
		assertEquals(1, v.getY());
		v.translate(new Vector2D(3, 4));
		assertEquals(4, v.getX());
		assertEquals(5, v.getY());
	}

	@Test
	public void translatedTest() {
		Vector2D v = new Vector2D(1, 1);
		assertEquals(1, v.getX());
		assertEquals(1, v.getY());
		Vector2D v2 = v.translated(new Vector2D(3, 4));
		assertEquals(4, v2.getX());
		assertEquals(5, v2.getY());
	}

	@Test
	public void rotateTest() {
		Vector2D v = new Vector2D(1, 1);
		assertEquals(1, v.getX());
		assertEquals(1, v.getY());
		v.rotate(Math.PI / 2);
		assertTrue(Math.abs(-1 - v.getX()) < 10e-8);
		assertTrue(Math.abs(1 - v.getY()) < 10e-8);
	}

	@Test
	public void rotatedTest() {
		Vector2D v = new Vector2D(1, 1);
		assertEquals(1, v.getX());
		assertEquals(1, v.getY());
		Vector2D v2 = v.rotated(Math.PI / 2);
		assertTrue(Math.abs(-1 - v2.getX()) < 10e-8);
		assertTrue(Math.abs(1 - v2.getY()) < 10e-8);
	}

	@Test
	public void scaleTest() {
		Vector2D v = new Vector2D(1, 2);
		v.scale(3);
		assertEquals(3, v.getX());
		assertEquals(6, v.getY());
	}

	@Test
	public void scaledTest() {
		Vector2D v = new Vector2D(1, 2);
		Vector2D v2 = v.scaled(3);
		assertEquals(3, v2.getX());
		assertEquals(6, v2.getY());
	}

	@Test
	public void copyTest() {
		Vector2D v = new Vector2D(1, 2);
		Vector2D v2 = v.copy();
		assertEquals(1, v2.getX());
		assertEquals(2, v2.getY());
	}
}
