package hr.fer.zemris.java.hw03.prob1;

/**
 * Simple enumeration that describes states in which our lexer can be. Available
 * states are BASIC and EXTENDED
 * 
 * @author Matej Fureš
 *
 */
public enum LexerState {
	/**
	 * Represents default lexer state
	 */
	BASIC,

	/**
	 * Represents lexer state in which lexer reads between 2 "#" signs
	 */
	EXTENDED;
}
