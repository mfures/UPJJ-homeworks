package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.FilterResult;

/**
 * Always writes correct string in string builder
 * 
 * @author Matej Fureš
 *
 */
public class StringNameBuilder implements NameBuilder {
	/**
	 * String to be added
	 */
	private String value;

	/**
	 * Sets string to be written. Mustn't be null
	 * 
	 * @param value to be written
	 * @throws NullPointerException if string is null
	 */
	public StringNameBuilder(String value) {
		Objects.requireNonNull(value);

		this.value = value;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		Objects.requireNonNull(result);
		Objects.requireNonNull(sb);

		sb.append(value);
	}

}
