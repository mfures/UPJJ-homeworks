package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	@Test
	public void exampleTestFromAssignment() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("1", "Bosnjan", "Pero", 2);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertTrue(recordSatisfies);
		record = new StudentRecord("1", "Boksnjan", "Pero", 2);
		recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertFalse(recordSatisfies);

	}

	@Test
	public void testWithOperatorLessAndName() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Ana",
				ComparisonOperators.LESS);
		StudentRecord record = new StudentRecord("1", "Bosnjan", "Jasna", 2);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertFalse(recordSatisfies);
		record = new StudentRecord("1", "Boksnjan", "An", 2);
		recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testWithOperatorGreaterAndJmbag() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, "4",
				ComparisonOperators.GREATER);
		StudentRecord record = new StudentRecord("3", "Bosnjan", "Jasna", 2);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertFalse(recordSatisfies);
		record = new StudentRecord("5", "Boksnjan", "An", 2);
		recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertTrue(recordSatisfies);
	}
}
