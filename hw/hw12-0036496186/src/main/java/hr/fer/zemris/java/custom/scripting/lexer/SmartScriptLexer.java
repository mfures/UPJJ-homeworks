package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Lexer for document format as described in homework assignment
 * 
 * @author Matej Fureš
 *
 */
public class SmartScriptLexer {
	/**
	 * Array generated from input String
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private SmartScriptToken token;

	/**
	 * Next index for parsing
	 */
	private int currentIndex;

	/**
	 * Current LexerState state of lexer
	 */
	private SmartScriptLexerState state;

	/**
	 * Creates Lexer from input String. If text is null, exception is thrown.
	 * 
	 * @param text to be parsed
	 * @throws NullPointerException if text is null
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text);

		data = text.toCharArray();
		state = SmartScriptLexerState.BASIC_TEXT;
	}

	/**
	 * Returns currently held token, multiple calls of this method don't change the
	 * token.
	 * 
	 * @return this lexers current token
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Setter for state property. Property mustn't be null.
	 * 
	 * @param state to set
	 * @throws NullPointerException if set is null
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state);

		this.state = state;
	}

	/**
	 * Generates and returns the next token for current input text. Every call of
	 * this method changes the value to next token. If lexer has finished with
	 * tokenization of input String it will return Token with TokenType
	 * type==TokenType.EOF, next call of this method will throw an exception.
	 * 
	 * @return next generated token
	 * @throws SmartScriptLexerException if token has to generate next token after
	 *                                   he was finished with input, or if input is
	 *                                   invalid
	 */
	public SmartScriptToken nextToken() {
		isPositionOutOfBounds();

		if (currentIndex == data.length) {
			return generateEOFToken();
		}

		return getNextTokenInCurrentState();
	}

	/**
	 * Method throws an exception if following statement is true:
	 * currentIndex>data.length
	 * 
	 * @throws SmartScriptLexerException is currentIndex is to large
	 */
	private void isPositionOutOfBounds() {
		if (currentIndex == data.length + 1) {
			throw new SmartScriptLexerException();
		}
	}

	/**
	 * Generates EOF token and updates token value. This method never throws.
	 * 
	 * @return EOF token
	 */
	private SmartScriptToken generateEOFToken() {
		return createAndUpdateToken(SmartTokenType.EOF, null, 1);
	}

	/**
	 * Constructs next token in accordance with current state
	 * 
	 * @return next token
	 */
	private SmartScriptToken getNextTokenInCurrentState() {

		switch (state) {
		case BASIC_TEXT:
			if (areOpenedBracesNext()) {
				return createAndUpdateToken(SmartTokenType.TAG_OPENED, "{$", 2);
			}

			return createAndUpdateToken(SmartTokenType.TEXT, getNextText(), 0);
		case TAG_TEXT:
			skipAllBlanks();

			SmartTokenType tagTextType = getTagTextType();
			return handleTypeInTag(tagTextType);
		}
		throw new SmartScriptLexerException();
	}

	/**
	 * Method reads from data array to read next text String. String ends before
	 * first "{$"
	 * 
	 * @return next text string
	 */
	private String getNextText() {
		String nextValue = tryNextPositionForText(), text = "";
		while (!nextValue.equals("")) {
			text += nextValue;
			nextValue = tryNextPositionForText();
		}
		return text;
	}

	private String tryNextPositionForText() {
		if (currentIndex == data.length) {
			return "";
		}

		if (currentIndex == data.length - 1) {
			return getNextCharacterToString();
		}

		if (data[currentIndex] == '{') {
			if (data[currentIndex + 1] == '$') {
				return "";
			}
		}

		if (data[currentIndex] == '\\') {
			if (data[currentIndex + 1] == '\\') {
				currentIndex += 2;
				return "\\";
			}

			if (data[currentIndex + 1] == '{') {
				currentIndex++;
				return getNextCharacterToString();
			}
		}

		return getNextCharacterToString();
	}

	/**
	 * Method increases current index, and returns next character
	 * 
	 * @return String representation of next character in data array
	 */
	private String getNextCharacterToString() {
		currentIndex++;
		return String.valueOf(data[currentIndex - 1]);
	}

	/**
	 * Helping method that returns true only if character on position currentIndex
	 * is '{' and character on position currentIndex+1 is '$'
	 * 
	 * @return true only if character on position currentIndex is '{' and character
	 *         on position currentIndex+1 is '$', false otherwise
	 */
	private boolean areOpenedBracesNext() {
		if (currentIndex == data.length || currentIndex == data.length - 1) {
			return false;
		}

		if (data[currentIndex] == '{') {
			if (data[currentIndex + 1] == '$') {
				return true;
			}
		}

		return false;
	}

	/**
	 * Helping method that returns true only if character on position currentIndex
	 * is '$' and character on position currentIndex+1 is '}'
	 * 
	 * @return true only if character on position currentIndex is '$' and character
	 *         on position currentIndex+1 is '}', false otherwise
	 */
	private boolean areClosedBracesNext() {
		if (currentIndex == data.length || currentIndex == data.length - 1) {
			return false;
		}

		if (data[currentIndex] == '$') {
			if (data[currentIndex + 1] == '}') {
				return true;
			}
		}

		return false;
	}

	/**
	 * Skips all blanks. If whole data array is skipped, an exception is thrown.
	 * 
	 * @throws SmartScriptLexerException when there are only blanks
	 */
	private void skipAllBlanks() {
		while (isBlankOnCurrentPosition(0)) {
			if (currentIndex == data.length) {
				throw new SmartScriptLexerException();
			}

			currentIndex++;
		}
	}

	/**
	 * Determines type for next token. If no type if determined, an exception is
	 * thrown.
	 * 
	 * @return SmartTokenType type
	 * @throws SmartScriptLexerException if type cannot be determines
	 */
	private SmartTokenType getTagTextType() {
		if (data[currentIndex] == '"') {
			return SmartTokenType.QUOTATION_MARK;
		}

		if (data[currentIndex] == '@') {
			return SmartTokenType.FUNCTION;
		}

		if (Character.isLetter(data[currentIndex])) {
			return SmartTokenType.VARIABLE;
		}

		// All double numbers start as integers
		if (isIntegerNext()) {
			return SmartTokenType.INTEGER;
		}

		if (isCharOperator(data[currentIndex])) {
			return SmartTokenType.OPERATOR;
		}

		if (areClosedBracesNext()) {
			return SmartTokenType.TAG_CLOSED;
		}

		throw new SmartScriptLexerException();
	}

	/**
	 * Checks if char is operator as defined in {@link Operator}
	 * 
	 * @param c char to test
	 * @return true only if char is one of operators in {@link Operator}, false
	 *         otherwise
	 */
	private boolean isCharOperator(char c) {
		if (c == '+') {
			return true;
		}

		if (c == '-') {
			return true;
		}

		if (c == '*') {
			return true;
		}

		if (c == '/') {
			return true;
		}

		if (c == '^') {
			return true;
		}

		if (c == '=') {
			return true;
		}

		return false;
	}

	/**
	 * Checks if next token should be INTEGER
	 * 
	 * @return true only if for is next
	 */
	private boolean isIntegerNext() {
		if (Character.isDigit(data[currentIndex])) {
			return true;
		} else {
			if (data[currentIndex] == '-') {
				if (isFollowingCharDigit()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Creates appropriate {@link SmartScriptToken} for given type. If it can't
	 * create a token, an exception is thrown
	 * 
	 * @param type {@link SmartTokenType} type
	 * @return {@link SmartScriptToken} token
	 * @throws SmartScriptLexerException if token can't be made
	 */
	private SmartScriptToken handleTypeInTag(SmartTokenType type) {
		switch (type) {
		case FUNCTION:
			String functionName = "";
			currentIndex++;

			functionName += getAllDigitsLettersAndUnderscoresInARow();

			return createAndUpdateToken(type, functionName, 0);

		case INTEGER:
			String intNumS = "";

			if (data[currentIndex] == '-') {
				intNumS = "-";
				currentIndex++;
			}

			intNumS += getAllDigitsInARow();

			if (data[currentIndex] == '.') {
				intNumS += ".";
				currentIndex++;

				intNumS += getAllDigitsInARow();

				if (intNumS.charAt(intNumS.length() - 1) == '.') {
					throw new SmartScriptLexerException();
				}

				Double doubleNum2;
				try {
					doubleNum2 = Double.parseDouble(intNumS);
				} catch (NumberFormatException e) {
					throw new SmartScriptLexerException();
				}

				return createAndUpdateToken(SmartTokenType.DOUBLE, doubleNum2, 0);
			}

			Integer intNum;
			try {
				intNum = Integer.parseInt(intNumS);
			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException();
			}

			return createAndUpdateToken(type, intNum, 0);

		case OPERATOR:
			return createAndUpdateToken(type, getOperatorFromChar(data[currentIndex]), 1);

		case QUOTATION_MARK:
			currentIndex++;

			String text = getTextBeforeQuotationSign();

			if (data.length == currentIndex) {
				throw new SmartScriptLexerException();
			}

			return createAndUpdateToken(SmartTokenType.STRING, text, 1);

		case TAG_CLOSED:
			return createAndUpdateToken(type, "$}", 2);

		case VARIABLE:
			return createAndUpdateToken(type, getAllDigitsLettersAndUnderscoresInARow(), 0);
		default:
			break;
		}

		throw new SmartScriptLexerException();
	}

	/**
	 * Creates new SmartScriptToken token, sets this instances property token to it,
	 * increments currentIndex by currentPositionIncrement and returns token
	 * property. If token was incorrectly created, exception is thrown.
	 * 
	 * @param type                     {@link SmartTokenType} type
	 * @param value                    {@link SmartTokenType} value
	 * @param currentPositionIncrement increment for currentIndex
	 * @return {@link SmartScriptToken} token
	 * @throws SmartScriptLexerException is Token wasn't created
	 */
	private SmartScriptToken createAndUpdateToken(SmartTokenType type, Object value, int currentPositionIncrement) {
		try {
			token = new SmartScriptToken(type, value);
		} catch (Exception e) {
			throw new SmartScriptLexerException();
		}

		currentIndex += currentPositionIncrement;
		return token;
	}

	/**
	 * Returns {@link Operator} in accordance to char c. If c isn't defined in
	 * {@link Operator}, an exception is thrown.
	 * 
	 * @param c char to test
	 * @return Operator determined by c
	 * @throws SmartScriptLexerException if c isn't supported in {@link Operator}
	 */
	private Operator getOperatorFromChar(char c) {
		if (c == '+') {
			return Operator.PLUS;
		}
		if (c == '-') {
			return Operator.MINUS;
		}
		if (c == '*') {
			return Operator.MULTIPLICATION;
		}
		if (c == '/') {
			return Operator.DIVISION;
		}
		if (c == '^') {
			return Operator.POWER;
		}
		if (c == '=') {
			return Operator.EQUAL_SIGN;
		}

		throw new SmartScriptLexerException();
	}

	/**
	 * Returns biggest row of digits from current position
	 * 
	 * @return String representation of a number
	 */
	private String getAllDigitsInARow() {
		String s = "";

		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex])) {
				s += getNextCharacterToString();
			} else {
				break;
			}
		}

		return s;
	}

	/**
	 * Returns biggest row of digits from current position
	 * 
	 * @return String representation of a number
	 */
	private String getAllDigitsLettersAndUnderscoresInARow() {
		String s = "";
		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex]) || Character.isLetter(data[currentIndex])
					|| data[currentIndex] == '_') {

				s += getNextCharacterToString();
			} else {
				break;
			}
		}

		return s;
	}

	/**
	 * Returns all text before next quotation sign
	 * 
	 * @return String of text before next quotation sign
	 * @throws SmartScriptLexerException if String isn't in acceptable format
	 */
	private String getTextBeforeQuotationSign() {
		String text = "";

		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\') {
				if (isAcceptableEscapeSequenceInString()) {
					text += getNextEscapedCharacterToString();
					continue;
				} else {
					throw new SmartScriptLexerException();
				}
			}
			if (data[currentIndex] != '"') {
				text += getNextCharacterToString();
			} else {
				break;
			}
		}

		return text;
	}

	private String getNextEscapedCharacterToString() {
		currentIndex += 2;

		if (data[currentIndex - 1] == 'n') {
			return "\n";
		}

		if (data[currentIndex - 1] == 'r') {
			return "\r";
		}

		if (data[currentIndex - 1] == 't') {
			return "\t";
		}

		if (data[currentIndex - 1] == '\\') {
			return "\\";
		}

		return "\"";
	}

	/**
	 * Method checks if character following '/' is one of acceptable characters in
	 * String mode. Acceptable Escape Sequences: "\n", "\t", "\r", "\\", '\"'.
	 * 
	 * @return true only if sequence is acceptable
	 */
	private boolean isAcceptableEscapeSequenceInString() {
		if (data.length == currentIndex + 1) {
			return false;
		}

		if (data[currentIndex + 1] == 'n') {
			return true;
		}

		if (data[currentIndex + 1] == 'r') {
			return true;
		}

		if (data[currentIndex + 1] == 't') {
			return true;
		}

		if (data[currentIndex + 1] == '\\') {
			return true;
		}

		if (data[currentIndex + 1] == '"') {
			return true;
		}

		return false;
	}

	/**
	 * Checks if char following char on position currentIndex can be interpreted as
	 * a digit
	 * 
	 * @return true only if following char can be interpreted as a digit
	 */
	private boolean isFollowingCharDigit() {
		if (data.length == currentIndex + 1) {
			return false;
		}

		return Character.isDigit(data[currentIndex + 1]);
	}

	/**
	 * Checks position for blanks. Allowed blanks are: '\n', '\r' and '\t'
	 * 
	 * @param offset checks data[currentIndex+offset] for blanks
	 * @return true only if character on said position is allowed blank
	 */
	private boolean isBlankOnCurrentPosition(int offset) {
		if (data.length == currentIndex + offset) {
			return false;
		}

		if (data[currentIndex + offset] == ' ') {
			return true;
		}

		if (data[currentIndex + offset] == '\n') {
			return true;
		}

		if (data[currentIndex + offset] == '\r') {
			return true;
		}

		if (data[currentIndex + offset] == '\t') {
			return true;
		}

		return false;
	}
}
