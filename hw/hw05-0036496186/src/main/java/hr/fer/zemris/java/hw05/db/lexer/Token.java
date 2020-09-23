package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Defines token for lexer and parser
 * 
 * @author Matej Fureš
 *
 */
public class Token {
	/**
	 * SmartTokens type
	 */
	private TokenType type;

	/**
	 * Tokens value
	 */
	private Object value;

	/**
	 * Constructor for Token. Accepted pairs of input values: <br>
	 * (SmartTokenType.EOL,null), <br>
	 * (SmartTokenType.OPERATOR,(ComparisonOperator)), <br>
	 * (SmartTokenType.QUOTATION_MARK,'"'), <br>
	 * (SmartTokenType.VARIABLE,(String)), <br>
	 * (SmartTokenType.STRING,(String)). <br>
	 * Any other combination throws an exception. If null is paired with anything
	 * other than TokenType.EOF, an exception is thrown.
	 * 
	 * @param type  Tokens TokenType type
	 * @param value Tokens object value
	 * @throws IllegalArgumentException when (type,value) isn't defined as valid
	 *                                  pair
	 * @throws NullPointerException     if type is null
	 */
	public Token(TokenType type, Object value) {
		Objects.requireNonNull(type);
		areArgumentsPairedCorrectly(type, value);

		this.type = type;
		this.value = value;
	}

	/**
	 * Returns tokens value
	 * 
	 * @return tokens value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns tokens type
	 * 
	 * @return tokens type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Method throws an exception if value isn't instance of String
	 * 
	 * @param value to be checked
	 * @throws IllegalArgumentException if value isn't instance of String
	 */
	private void isInstanceOfString(Object value) {
		if (!(value instanceof String)) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method checks if {@code type} and {@code value} are paired as defined for
	 * Token. If they are not paired correctly, an exception is thrown.
	 * 
	 * @param type Token type
	 * @param value Object value
	 * @throws IllegalArgumentException if arguments are paired correctly
	 */
	public void areArgumentsPairedCorrectly(TokenType type, Object value) {
		switch (type) {
		case EOL:
			if (value != null) {
				throw new IllegalArgumentException();
			}
			return;

		case OPERATOR:
			if (!(value instanceof ComparisonOperator)) {
				throw new IllegalArgumentException();
			}
			return;

		case QUOTATION_MARK:
			if (!(value.equals('"'))) {
				throw new IllegalArgumentException();
			}
			return;

		case VARIABLE:
			isInstanceOfString(value);
			return;

		case STRING:
			isInstanceOfString(value);
			return;

		default:
			throw new IllegalArgumentException();
		}
	}

}