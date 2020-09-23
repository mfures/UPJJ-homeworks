package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Lexer for for generating tokens for queries used in program
 * 
 * @author Matej Fureš
 *
 */
public class QueryLexer {
	/**
	 * Array generated from input String
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private Token token;

	/**
	 * Next index for parsing
	 */
	private int currentIndex;

	/**
	 * Creates Lexer from input String. If input is null an exception will be
	 * thrown.
	 * 
	 * @param input to be parsed
	 * @throws NullPointerException if input == null
	 */
	public QueryLexer(String input) {
		Objects.requireNonNull(input);

		data = input.toCharArray();
	}

	/**
	 * Returns currentToken
	 * 
	 * @return lexers current token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Generates and returns the next token for current input text. If lexer has
	 * returned EOL token, every next call of this function throws an exception
	 * 
	 * @return next token
	 * @throws LexerException if format is invalid, or token has completed
	 */
	public Token nextToken() {
		requireValidPosition();
		skipBlanks();

		if (currentIndex == data.length) {
			return createAndUpdateToken(TokenType.EOL, null, 1);
		}

		return generateNextToken();
	}

	/**
	 * Generates next token for lexer
	 * 
	 * @return next token
	 */
	private Token generateNextToken() {
		TokenType type = determineType();

		return handleType(type);
	}

	/**
	 * Creates new token in accordance with calculated TokenType type, if it can't,
	 * an exception is thrown
	 * 
	 * @param type for which token is resolved
	 * @return next token
	 * @throws LexerException if token can't be resolved
	 */
	private Token handleType(TokenType type) {
		switch (type) {
		case OPERATOR:
			if (data[currentIndex] == '=') {
				return createAndUpdateToken(type, ComparisonOperator.EQUALS, 1);
			}

			if (data[currentIndex] == '!') {
				if (currentIndex + 1 == data.length) {
					throw new LexerException("Can't resolve token value");
				}

				if (data[currentIndex + 1] != '=') {
					throw new LexerException("Can't resolve token value");
				}
				return createAndUpdateToken(type, ComparisonOperator.NOT_EQUALS, 2);
			}

			if (currentIndex + 1 != data.length) {
				if (data[currentIndex + 1] == '=') {
					if (data[currentIndex] == '<') {
						return createAndUpdateToken(type, ComparisonOperator.LESSER_OR_EQUAL, 2);
					}

					if (data[currentIndex] == '>') {
						return createAndUpdateToken(type, ComparisonOperator.GREATER_OR_EQUAL, 2);
					}
				}
			}

			if (data[currentIndex] == '<') {
				return createAndUpdateToken(type, ComparisonOperator.LESSER, 1);
			}

			return createAndUpdateToken(type, ComparisonOperator.GREATER, 1);
		case QUOTATION_MARK:
			String value = processString();
			return createAndUpdateToken(TokenType.STRING, value, 1);
		case VARIABLE:
			return createAndUpdateToken(type, processVariable(), 0);
		default:
			throw new LexerException("Unsupported type.");
		}
	}

	/**
	 * Collects all characters in data array until next operator, ", space or '\t'
	 * sign.
	 * 
	 * @return variable
	 */
	private String processVariable() {
		String s = "";

		while (data.length  > currentIndex) {
			if (data[currentIndex] == '"' || isBlank() || isOperator(0)) {
				return s;
			}

			s += String.valueOf(data[currentIndex]);
			currentIndex++;
		}

		return s;
	}

	/**
	 * Collects all characters in data array until next " sign. If it isn't found,
	 * an exception is thrown.
	 * 
	 * @return new String
	 * @throws LexerException if no " is found
	 */
	private String processString() {
		currentIndex++;
		String s = "";

		while (data.length > currentIndex) {
			if (data[currentIndex] == '"') {
				return s;
			}

			s += String.valueOf(data[currentIndex]);
			currentIndex++;
		}

		throw new LexerException("String tag wasn't closed");
	}

	/**
	 * Determines type for next token
	 * 
	 * @return next tokens type
	 * @throws LexerException if type can't be resolved
	 */
	private TokenType determineType() {
		if (Character.isLetter(data[currentIndex])) {
			return TokenType.VARIABLE;
		}

		if (data[currentIndex] == '"') {
			return TokenType.QUOTATION_MARK;
		}

		if (isOperator(0)) {
			return TokenType.OPERATOR;
		}

		throw new LexerException("Unsupported type.");
	}

	/**
	 * Checks if char at position currentIndex+offset is operator
	 * 
	 * @param offset from currentIndex
	 * @return true if said character is operator
	 */
	private boolean isOperator(int offset) {
		if (currentIndex + offset == data.length) {
			return false;
		}

		switch (data[currentIndex]) {
		case '>':
			return true;

		case '<':
			return true;

		case '=':
			return true;

		case '!':
			return true;
		}

		return false;
	}

	/**
	 * Method skips all indexes from current, that are '\t' or ' '
	 */
	private void skipBlanks() {
		while (currentIndex < data.length ) {
			if (!(isBlank())) {
				break;
			}

			currentIndex++;
		}
	}

	/**
	 * Checks if data[currentIndex] is ' ' or '\t', and if is returns true
	 * 
	 * @return true if character is blank
	 */
	private boolean isBlank() {
		return data[currentIndex] == ' ' || data[currentIndex] == '\t';
	}

	/**
	 * Method throws an exception if following statement is true:
	 * currentIndex>data.length
	 * 
	 * @throws LexerException is currentIndex is to large
	 */
	private void requireValidPosition() {
		if (currentIndex == data.length + 1) {
			throw new LexerException("Lexer has completed process of processing input");
		}
	}

	/**
	 * Creates new token, updates currentPosition and returns newly generated token.
	 * If token wasn't generated, an exception is thrown.
	 * 
	 * @param type                     type of token
	 * @param value                    value of token
	 * @param currentPositionIncrement increment for currentIndex
	 * @return next token
	 * @throws LexerException is Token wasn't created
	 */
	private Token createAndUpdateToken(TokenType type, Object value, int currentPositionIncrement) {
		try {
			token = new Token(type, value);
		} catch (Exception e) {
			throw new LexerException("Couldn't create valid token.");
		}

		currentIndex += currentPositionIncrement;
		return token;
	}
}
