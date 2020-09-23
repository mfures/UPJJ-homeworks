package hr.fer.zemris.java.hw03.prob1;

/**
 * Simple enumeration used in example lexer. Values represent allowed TokenTypes
 * for our parser.
 * 
 * @author Matej Fureš
 *
 */
public enum TokenType {
	/**
	 * Represents end of file
	 */
	EOF,
	/**
	 * Represents a string contained only of letters; numbers and / are also
	 * included if they are escaped by /
	 */
	WORD,
	/**
	 * Represents a number that fits into long
	 */
	NUMBER,
	/**
	 * Represents a character
	 */
	SYMBOL;
}
