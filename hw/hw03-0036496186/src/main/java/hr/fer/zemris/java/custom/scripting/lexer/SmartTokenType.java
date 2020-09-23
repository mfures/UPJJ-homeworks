package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Simple enumeration used in our lexer. Values represent allowed TokenTypes for
 * our parser. Available types:<br>
 * EOF, <br>
 * TEXT, <br>
 * TAG_OPENED, <br>
 * TAG_CLOSED, <br>
 * QUOTATION_MARK, <br>
 * DOUBLE, <br>
 * INTEGER, <br>
 * FUNCTION, <br>
 * OPERATOR, <br>
 * VARIABLE, <br>
 * TAG_NAME, <br>
 * STRING.
 * 
 * @author Matej Fureš
 *
 */
public enum SmartTokenType {
	/**
	 * Represents end of file
	 */
	EOF,
	/**
	 * Represents a string found before "{$" and after "$}"
	 */
	TEXT,
	/**
	 * Represents "{$" String
	 */
	TAG_OPENED,
	/**
	 * Represents "$}" String
	 */
	TAG_CLOSED,
	/**
	 * Represents '"' String
	 */
	QUOTATION_MARK,

	/**
	 * Represents a double number
	 */
	DOUBLE,

	/**
	 * Represents an integer
	 */
	INTEGER,

	/**
	 * Represents a string that starts with '@', continues with a letter, and is
	 * made of letters, numbers and underscores e.g @func_1
	 */
	FUNCTION,

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
	 * Represents one of valid tags defined in {@link ValidTag}
	 */
	TAG_NAME,

	/**
	 * Represents String value found between two '"' signs
	 */
	STRING;
}
