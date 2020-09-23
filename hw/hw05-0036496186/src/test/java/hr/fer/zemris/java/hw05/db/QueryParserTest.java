package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	@Test
	public void testNull() {
		assertThrows(NullPointerException.class, () -> new QueryParser(null));
	}

	@Test
	public void testEmpty() {
		assertThrows(QueryParserException.class, () -> new QueryParser(""));
	}

	@Test
	public void incorrectVariable() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbak"));
	}

	@Test
	public void correctVariable() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag"));
	}

	@Test
	public void correctVariableAndOperator() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag="));
	}

	@Test
	public void directQuery() {
		QueryParser p = new QueryParser("jmbag=\"003\"");
		assertTrue(p.isDirectQuery());
		assertEquals("003", p.getQueriedJMBAG());
		assertEquals(1, p.getQuery().size());
	}

	@Test
	public void extraAndTest() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=\"003\"and"));
	}

	@Test
	public void complexQuery() {
		QueryParser p = new QueryParser("jmbag=\"003\"and lastName=\"mrak\"");
		assertFalse(p.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> p.getQueriedJMBAG());
		assertEquals(2, p.getQuery().size());
	}

	@Test
	public void assignmentExample() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery()); // true
		assertEquals("0123456789", qp1.getQueriedJMBAG()); // 0123456789
		assertEquals(1, qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse( qp2.isDirectQuery()); // false
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG()); // would throw!
		assertEquals(2, qp2.getQuery().size()); // 2
	}
}
