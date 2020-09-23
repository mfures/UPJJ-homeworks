package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Defines a token. Token has a value and type.
 * 
 * @author Matej Fureš
 *
 */
public class Token {
	/**
	 * Tokens type
	 */
	private TokenType type;

	/**
	 * Tokens value
	 */
	private Object value;

	/**
	 * Constructor for Token. Accepted pairs of input values:<br>
	 * (TokenType.EOF,null), <br>
	 * (TokenType.Number,(long)), <br>
	 * (TokenType.WORD,(String)), <br>
	 * (TokenType.SYMBOL, Character).<br>
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
		switch (type) {
		case EOF:
			if (value != null) {
				throw new IllegalArgumentException();
			}
			break;
		case NUMBER:
			if (!(value instanceof Long)) {
				throw new IllegalArgumentException();
			}
			break;
		case SYMBOL:
			if (!(value instanceof Character)) {
				throw new IllegalArgumentException();
			}
			break;
		case WORD:
			if (!(value instanceof String)) {
				throw new IllegalArgumentException();
			}
		}

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
}
