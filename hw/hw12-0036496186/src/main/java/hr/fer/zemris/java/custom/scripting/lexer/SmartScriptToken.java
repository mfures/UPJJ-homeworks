package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Defines a token used by SmartScriptLexer. Token has a value and type.
 * 
 * @author Matej Fureš
 *
 */
public class SmartScriptToken {
	/**
	 * SmartTokens type
	 */
	private SmartTokenType type;

	/**
	 * Tokens value
	 */
	private Object value;

	/**
	 * Constructor for Token. Accepted pairs of input values: <br>
	 * (SmartTokenType.EOF,null), <br>
	 * (SmartTokenType.TEXT,(String)), <br>
	 * (SmartTokenType.TAG_OPENED,"{$"), <br>
	 * (SmartTokenType.TAG_CLOSED,"$}"), <br>
	 * (SmartTokenType.DOUBLE,(Double)), <br>
	 * (SmartTokenType.FUNCTION,(String)), <br>
	 * (SmartTokenType.INTEGER,(Integer)), <br>
	 * (SmartTokenType.OPERATOR,(Operator)), <br>
	 * (SmartTokenType.QUOTATION_MARK,'"'), <br>
	 * (SmartTokenType.DOUBLE,(Double)), <br>
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
	public SmartScriptToken(SmartTokenType type, Object value) {
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
	public SmartTokenType getType() {
		return type;
	}

	/**
	 * Checks if variable is in valid format, if it isn't throws an exception. Valid
	 * variable e.g Var_2. Variable format is defined here: {@link SmartTokenType}
	 * 
	 * @param input string to be tested
	 * @throws IllegalArgumentException if input isn't well formated
	 */
	private void isVariableInGoodFormat(String input) {
		if (input.length() == 0) {
			throw new IllegalArgumentException();
		}

		if (!Character.isLetter(input.charAt(0))) {
			throw new IllegalArgumentException();
		}

		for (Character c : input.substring(1).toCharArray()) {
			if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
				continue;
			}

			throw new IllegalArgumentException();
		}
	}

	/**
	 * Checks if function is in valid format, if it isn't throws an exception. Valid
	 * variable e.g @Func_2. Function format is defined here: {@link SmartTokenType}
	 * 
	 * @param input string to be tested
	 * @throws IllegalArgumentException if input isn't well formated
	 */
	public void isFunctionInGoodFormat(String input) {
		isVariableInGoodFormat(input);
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
	 * Method checks if {@code type} and {@code value} are paired as defined
	 * {@link #SmartScriptToken(SmartTokenType, Object)}. If they are not paired
	 * correctly, an exception is thrown.
	 * 
	 * @param type  SmartToken type
	 * @param value Object value
	 * @throws IllegalArgumentException if arguments are paired correctly
	 */
	public void areArgumentsPairedCorrectly(SmartTokenType type, Object value) {
		switch (type) {
		case EOF:
			if (value != null) {
				throw new IllegalArgumentException();
			}
			return;
		case TEXT:
			isInstanceOfString(value);
			return;
		case TAG_OPENED:
			if (!(value.equals("{$"))) {
				throw new IllegalArgumentException();
			}
			return;
		case TAG_CLOSED:
			if (!(value.equals("$}"))) {
				throw new IllegalArgumentException();
			}
			return;
		case DOUBLE://
			if (!(value instanceof Double)) {
				throw new IllegalArgumentException();
			}
			return;
		case FUNCTION://
			isInstanceOfString(value);
			isFunctionInGoodFormat((String) value);
			return;
		case INTEGER://
			if (!(value instanceof Integer)) {
				throw new IllegalArgumentException();
			}
			return;
		case OPERATOR://
			if (!(value instanceof Operator)) {
				throw new IllegalArgumentException();
			}
			return;
		case QUOTATION_MARK://
			if (!(value.equals('"'))) {
				throw new IllegalArgumentException();
			}
			return;
		case VARIABLE://
			isInstanceOfString(value);
			isVariableInGoodFormat((String) value);
			return;
		case STRING:
			isInstanceOfString(value);
			return;
		default:
			throw new IllegalArgumentException();
		}
	}

}
