package hr.fer.zemris.math.util.cparser;

import java.util.Objects;

/**
 * Lexer for Complex numbers
 * 
 * @author matfures
 *
 */
public class ComplexNumberLexer {
	/**
	 * Stores input data as char array from string
	 */
	private char[] data;

	/**
	 * Current index in data array
	 */
	private int currentIndex;

	/**
	 * Builds next string to be parsed as double
	 */
	private StringBuilder currentValue;

	/**
	 * Currently made wrapper
	 */
	private DoubleWrapper current;

	/**
	 * Creates lexer. Input can't be null
	 * 
	 * @param input to be parsed
	 * @throws NullPointerException if input is null
	 */
	public ComplexNumberLexer(String input) {
		Objects.requireNonNull(input);

		data = input.toCharArray();
		currentValue = new StringBuilder();
	}

	/**
	 * Returns next number in string. If lexer has no more numbers to return, it
	 * returns null. If user has called this method after he received null, this
	 * method will throw an exception. If this method can't parse something, it will
	 * throw an exception
	 * 
	 * @return next number in wrapper container
	 * @throws ComplexNumberParsingException if any error occurs
	 */
	public DoubleWrapper next() {
		if (currentIndex >data.length) {
			throw new ComplexNumberParsingException("Next called when lexer was done with tokenization");
		}

		skipAllBlanks();
		if (currentIndex == data.length) {
			currentIndex++;
			return null;
		}

		currentValue.setLength(0);
		current = new DoubleWrapper();

		int sign = getSign();
		skipAllBlanks();
		if (currentIndex == data.length) {
			throw new ComplexNumberParsingException("Parsing error: stray " + data[currentIndex - 1]);
		}

		isImaginary();
		skipAllBlanks();
		if (currentIndex == data.length) {
			current.setValue(sign);
			return current;
		}

		buildNumber();

		try {
			current.setValue(sign * Double.parseDouble(currentValue.toString()));
		} catch (Exception e) {
			throw new ComplexNumberParsingException("Couldnt parse number: " + currentValue.toString());
		}

		return current;
	}

	private void buildNumber() {
		while (currentIndex < data.length) {
			if (!Character.isDigit(data[currentIndex])) {
				if (!(data[currentIndex] == '.')) {
					return;
				}
			}

			currentValue.append(data[currentIndex]);
			currentIndex++;
		}
	}

	private void isImaginary() {
		if (data[currentIndex] == 'i') {
			currentIndex++;
			current.setImaginary(true);
		}
	}

	/**
	 * Gets sign for next number
	 * 
	 * @return sign of next number
	 */
	private int getSign() {

		if (data[currentIndex] == '-') {
			currentIndex++;
			current.setSigned(true);
			return -1;
		}

		if (data[currentIndex] == '+') {
			currentIndex++;
			current.setSigned(true);
		}

		return 1;
	}

	/**
	 * Skips blanks up to first not blank position. Blanks are tabs and spaces
	 */
	private void skipAllBlanks() {
		while (currentIndex < data.length) {
			if (!isBlank()) {
				return;
			}

			currentIndex++;
		}
	}

	/**
	 * Checks if char on current position is space or tab, and if so, returns true,
	 * false otherwise
	 * 
	 * @return returns true if position is tab or space, false otherwise
	 */
	private boolean isBlank() {
		return data[currentIndex] == ' ' || data[currentIndex] == '\t';
	}
}
