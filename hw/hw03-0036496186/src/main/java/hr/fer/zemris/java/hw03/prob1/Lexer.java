package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Lexer for document format as described in homework assignment
 * 
 * @author Matej Fureš
 *
 */
public class Lexer {
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
	 * Current LexerState state of lexer
	 */
	private LexerState state;

	/**
	 * Creates Lexer from input String. If text is null, exception is thrown.
	 * 
	 * @param text to be parsed
	 * @throws NullPointerException if text is null
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);

		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Generates and returns the next token for current input text. Every call of
	 * this method changes the value to next token. If lexer has finished with
	 * tokenization of input String it will return Token with TokenType
	 * type==TokenType.EOF, next call of this method will throw an exception.
	 * 
	 * @return next generated token
	 * @throws LexerException if token has to generate next token after he was
	 *                        finished with input, or if input is invalid
	 */
	public Token nextToken() {
		if (currentIndex == data.length + 1) {
			throw new LexerException();
		}

		while (isBlank()) {
			currentIndex++;
		}

		if (currentIndex == data.length) {
			currentIndex++;
			token = new Token(TokenType.EOF, null);
			return token;
		}

		return getNextTokenInCurrentState();
	}

	/**
	 * Constructs next token in accordance with current state
	 * 
	 * @return next token
	 */
	private Token getNextTokenInCurrentState() {

		switch (state) {
		case BASIC:
			TokenType type = determineCurrentType();
			String value = determineCurrentValue(type);

			if (type == TokenType.WORD) {
				token = new Token(TokenType.WORD, value);

			} else {
				if (type == TokenType.NUMBER) {

					try {
						token = new Token(TokenType.NUMBER, Long.parseLong(value));
					} catch (NumberFormatException e) {
						throw new LexerException();
					}

				} else {
					setTokenForNextSymbol();
				}
			}

			return token;
		case EXTENDED:
			if (data[currentIndex] == '#') {
				setTokenForNextSymbol();

				return token;
			}

			token = new Token(TokenType.WORD, determineCurrentValueInExtendedMode());
			return token;
		}

		return null;
	}

	/**
	 * Returns longest possible String value that is compatible with current
	 * TokenType type.
	 * 
	 * @param type TokenType for Token that we want to create
	 * @return value of Token that we want to create
	 */
	private String determineCurrentValue(TokenType type) {
		if (type == TokenType.WORD) {
			String s = "", word = "";

			do {
				s = nextCharachterInWord();
				word += s;
			} while (!s.equals(""));

			return word;
		}
		if (type == TokenType.NUMBER) {
			String s = "", number = "";

			do {
				s = nextCharachterInNumber();
				number += s;
			} while (!s.equals(""));

			return number;
		}

		return "";
	}

	/**
	 * Returns currently held token, multiple calls of this method don't change the
	 * token.
	 * 
	 * @return this lexers current token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Checks if there is next available number in string and returns it. If there
	 * is none "" is returned
	 * 
	 * @return "" if next character is not number, String value of that number
	 *         otherwise
	 */
	private String nextCharachterInNumber() {
		if (currentIndex == data.length) {
			return "";
		}

		if (Character.isDigit(data[currentIndex])) {
			return incrementCounterAndReturnNextCharAsString(1);
		}

		return "";
	}

	/**
	 * Checks if next character in data array is representable in our word type. If
	 * it is, its returned as a String, otherwise empty String is returned.
	 * 
	 * @return String representation of next sign, if that sign is representable as
	 *         part of Word. Empty String otherwise.
	 */
	private String nextCharachterInWord() {
		if (currentIndex == data.length) {
			return "";
		}

		if (Character.isLetter(data[currentIndex])) {
			return incrementCounterAndReturnNextCharAsString(1);
		}

		if (data[currentIndex] == '\\') {
			if (currentIndex + 1 == data.length) {
				return "";
			}

			if (Character.isDigit(data[currentIndex + 1])) {
				return incrementCounterAndReturnNextCharAsString(2);
			}

			if (data[currentIndex + 1] == '\\') {
				return incrementCounterAndReturnNextCharAsString(2);
			}
		}

		return "";
	}

	/**
	 * Determines type for next token
	 * 
	 * @return TokenType type for next token
	 */
	private TokenType determineCurrentType() {
		if (Character.isDigit(data[currentIndex])) {
			return TokenType.NUMBER;
		}

		if (Character.isLetter(data[currentIndex])) {
			return TokenType.WORD;
		}

		if (data[currentIndex] == '\\') {
			if (currentIndex + 1 == data.length) {
				throw new LexerException();
			}

			if (Character.isDigit(data[currentIndex + 1])) {
				return TokenType.WORD;
			}

			if (data[currentIndex + 1] == '\\') {
				return TokenType.WORD;
			}

			throw new LexerException();
		}

		return TokenType.SYMBOL;
	}

	/**
	 * Returns true only if currentIndex is pointing at a blank that we should
	 * skip.('/t', '/r', '/n' and ' ')
	 * 
	 * @return
	 */
	private boolean isBlank() {
		if (currentIndex == data.length) {
			return false;
		}

		switch (data[currentIndex]) {
		case ' ':
			return true;

		case '\r':
			return true;

		case '\n':
			return true;

		case '\t':
			return true;
		}

		return false;

	}

	/**
	 * Increments currentIndex by increment and returns character at
	 * data[currentIndex-1]
	 * 
	 * @param increment value to add to currentIndex
	 * @return data[currentIndex-1]
	 */
	private String incrementCounterAndReturnNextCharAsString(int increment) {
		currentIndex += increment;
		return String.valueOf(data[currentIndex - 1]);
	}

	/**
	 * Sets lexers state to arguments value. Argument must be either
	 * LexerState.BASIC or LexerState.EXTENDED, otherwise exception is thrown. If
	 * state is null, exception is thrown.
	 * 
	 * @param state LexerState state to change to
	 * @throws NullPointerException     if state is null
	 * @throws IllegalArgumentException if state isn't BASIC or EXTENDED
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);

		if (state != LexerState.BASIC && state != LexerState.EXTENDED) {
			throw new IllegalArgumentException();
		}

		this.state = state;
	}

	/**
	 * Method that creates new token from next character, sets its value to token
	 * and increases currentIndex by 1
	 */
	private void setTokenForNextSymbol() {
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
	}

	/**
	 * Method longest word in data array from current position before next blank or
	 * "#"
	 * 
	 * @return next word in String format
	 */
	private String determineCurrentValueInExtendedMode() {
		String s = determineCurrentCharacterInExtendedMode(), value = "";

		while (!s.equals("")) {
			value += s;
			s = determineCurrentCharacterInExtendedMode();
		}

		return value;
	}

	/**
	 * Method finds next eligible character in data array from current position
	 * before next blank or "#"
	 * 
	 * @return next character in String format
	 */
	private String determineCurrentCharacterInExtendedMode() {
		if (currentIndex == data.length) {
			return "";
		}

		if (isBlank()) {
			return "";
		}

		if (data[currentIndex] == '#') {
			return "";
		}

		return incrementCounterAndReturnNextCharAsString(1);
	}
}