package hr.fer.zemris.math.util.cparser;

import java.util.Objects;

import hr.fer.zemris.math.Complex;

/**
 * Parser for parsing complex numbers. Complex numbers is of form a+ib or a-ib
 * where parts that are zero can be dropped, but not both. A and B can be in
 * following formats:<br>
 * <b>digits</b><br>
 * <b>digits-dot-digits</b><br>
 * Any other format will cause an exception
 * 
 * @author matfures
 *
 */
public class ComplexNumberParser {
	/**
	 * Value parsed from input string
	 */
	private Complex value;

	/**
	 * Lexer used in parsing
	 */
	private ComplexNumberLexer lexer;

	/**
	 * Parses given input to Complex. Input can't be null. If any error occurs, an
	 * exception will be thrown
	 * 
	 * @param input to be parsed
	 * @throws NullPointerException          if input is null
	 * @throws ComplexNumberParsingException if errors occur
	 */
	public ComplexNumberParser(String input) {
		Objects.requireNonNull(input);
		if (input.isEmpty()) {
			throw new ComplexNumberParsingException("Input was empty");
		}

		lexer = new ComplexNumberLexer(input);
		parse();
	}

	/**
	 * Parses input
	 * 
	 * @throws ComplexNumberParsingException if any error occurs
	 */
	private void parse() {
		DoubleWrapper current = lexer.next();

		if (current.isImaginary()) {
			value = new Complex(0, current.getValue());

			current = lexer.next();
			if (current != null) {
				throw new ComplexNumberParsingException("Imaginary component need to be last in string");
			}
			
			return;
		}

		value = new Complex(current.getValue(), 0);
		current = lexer.next();

		if (current == null) {
			return;
		}

		if (!current.isImaginary()) {
			throw new ComplexNumberParsingException("Second number wasn't imaginary");
		}
		if (!current.isSigned()) {
			throw new ComplexNumberParsingException("Second number wasn't signed");
		}

		value = value.add(new Complex(0, current.getValue()));
		current = lexer.next();

		if (current != null) {
			throw new ComplexNumberParsingException("Imaginary component need to be last in string");
		}
	}

	/**
	 * Returns value
	 * 
	 * @return value
	 */
	public Complex getValue() {
		return value;
	}
}
