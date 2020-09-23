package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	@Test
	public void LessTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void LessOrEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void GreaterTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void GreaterOrEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void equalsTest() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void notEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void LikeNoWildCardTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Marko je dobar", "Mark"));
	}

	@Test
	public void LikeWildCardOnBegginingTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Ana", "*Jasna"));
		assertTrue(oper.satisfied("Ana", "*Ana"));
		assertFalse(oper.satisfied("Ana", "*Anj"));
		assertFalse(oper.satisfied("Jasna", "*Ana"));
		assertTrue(oper.satisfied("Marko je dobar", "*ark"));
	}

	@Test
	public void LikeWildCardOnEndTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Ana", "Jasna*"));
		assertTrue(oper.satisfied("Ana", "Ana*"));
		assertTrue(oper.satisfied("Ana", "An*"));
		assertFalse(oper.satisfied("Jasna", "Ana*"));
		assertTrue(oper.satisfied("Marko je dobar", "Mar*"));
	}

	@Test
	public void LikeWildCardTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Ana", "Jas*na"));
		assertFalse(oper.satisfied("Ana", "An*na"));
		assertFalse(oper.satisfied("Ana", "A*ja"));
		assertTrue(oper.satisfied("Ana", "An*a"));
		assertTrue(oper.satisfied("Anka", "An*a"));
		assertTrue(oper.satisfied("Ana", "A*n"));
		assertFalse(oper.satisfied("Jasna", "An*a"));
		assertTrue(oper.satisfied("Marko je dobar", "Ma*k"));
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));

	}
}
