package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Simple enumeration used in our lexer. Values represent allowed TokenTypes for
 * our parser. Available types:<br>
 * EOL, <br>
 * QUOTATION_MARK, <br>
 * OPERATOR, <br>
 * VARIABLE, <br>
 * STRING.
 * 
 * @author Matej Fureš
 *
 */
public enum TokenType {
	/**
	 * Represents end of line
	 */
	EOL,
	
	/**
	 * Represents '"' String
	 */
	QUOTATION_MARK,

	/**
	 * Represents a String as defined in {@link Operator}
	 */
	OPERATOR,

	/**
	 * Represents a string that starts with a letter, and is made of letters,
	 * numbers and underscores e.g var_1
	 */
	VARIABLE,

	/**
	 * Represents String value found between two '"' signs
	 */
	STRING;
}
