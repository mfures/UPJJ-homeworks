package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Defines a class that compares given string to its literal in way defined
 * trough constructor
 * 
 * @author Matej Fureš
 *
 */
public class ConditionalExpression {
	/**
	 * Operator used for comparing given string to literal
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * String literal used in comparing operators operations
	 */
	private String stringLiteral;

	/**
	 * FieldValueGetter used for extracting fields from StudenRecord
	 */
	private IFieldValueGetter fieldGetter;

	/**
	 * Constructs ConditonalExpression from given fields. If any field is null, an
	 * exception will be thrown.
	 * 
	 * @param comparisonOperator operator whose operation is used
	 * @param stringLiteral      literal used in comparison operation
	 * @param fieldGetter        getter that extracts field field from StudentRecord
	 * @throws NullPointerException if any of arguments is null
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		Objects.requireNonNull(comparisonOperator);
		Objects.requireNonNull(stringLiteral);
		Objects.requireNonNull(fieldGetter);

		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
		this.fieldGetter = fieldGetter;
	}

	/**
	 * Method for retrieving value of comparisonOperator in this
	 * CondtionalExpression
	 * 
	 * @return value of comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Method for retrieving value of stringLiteral in this CondtionalExpression
	 * 
	 * @return value of stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Method for retrieving value of fieldGetter in this CondtionalExpression
	 * 
	 * @return value of fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
}
