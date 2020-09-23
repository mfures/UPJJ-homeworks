package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Simple enumeration that describes states in which our lexer can be. Available
 * states are:<br>
 * BASIC_TEXT, <br>
 * TAG_TEXT. <br>
 * 
 * @author Matej Fureš
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Represents default lexer state in which it reads simple text
	 */
	BASIC_TEXT,

	/**
	 * Represents state in which lexer is reading between "{$" and "$}"
	 */
	TAG_TEXT,
}
