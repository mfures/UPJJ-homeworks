package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Simple enumeration that describes simple operators. Provided operators
 * are:<br>
 * GREATER, <br>
 * LESSER, <br>
 * GREATER_OR_EQUAL, <br>
 * LESSER_OR_EGUAL,<br>
 * EQUALS, <br>
 * NOT_EQUALS.
 * 
 * @author Matej Fureš
 */
public enum ComparisonOperator {
	/**
	 * Represents >
	 */
	GREATER(">"),

	/**
	 * Represents <
	 */
	LESSER("<"),

	/**
	 * Represents >=
	 */
	GREATER_OR_EQUAL(">="),

	/**
	 * Represents <=
	 */
	LESSER_OR_EQUAL("<="),

	/**
	 * Represents =
	 */
	EQUALS("="),

	/**
	 * Represents !=
	 */
	NOT_EQUALS("!=");

	/**
	 * Comparison operators operator
	 */
	private String operator;

	/**
	 * Private constructor for initialization
	 * 
	 * @param operator
	 */
	private ComparisonOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Getter for operator
	 * 
	 * @return this operator
	 */
	public String getOperator() {
		return operator;
	}
}
