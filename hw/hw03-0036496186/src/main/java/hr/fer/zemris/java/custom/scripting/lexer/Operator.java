package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Simple enumeration that describes simple operators. Provided operators
 * are:<br>
 * PLUS, <br>
 * MINUS, <br>
 * DIVISION, <br>
 * EQUAL_SIGN,<br>
 * MULTIPLICATION, <br>
 * POWER.
 * 
 * @author Matej Fureš
 *
 */
public enum Operator {
	/**
	 * Represents "+"
	 */
	PLUS("+"),

	/**
	 * Represents "-"
	 */
	MINUS("-"),

	/**
	 * Represents "*"
	 */
	MULTIPLICATION("*"),

	/**
	 * Represents "/"
	 */
	DIVISION("/"),

	/**
	 * Represents "^"
	 */
	POWER("^"),
	/**
	 * Refers to "=" String
	 */
	EQUAL_SIGN("=");

	/**
	 * Operation that is represented
	 */
	private String operation;

	/**
	 * Private constructor.
	 * 
	 * @param operation to set
	 */
	private Operator(String operation) {
		this.operation = operation;
	}

	/**
	 * Returns string representation of operator
	 * 
	 * @return value of operation property
	 */
	public String getOperation() {
		return this.operation;
	}
}
