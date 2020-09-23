package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.FilterResult;

/**
 * Adds correct group on spot in string builder
 * 
 * @author Matej Fureš
 *
 */
public class GroupNameBuilder implements NameBuilder {
	/**
	 * Index of used group
	 */
	private int index;

	/**
	 * Padding used in string builder
	 */
	char padding;

	/**
	 * Minimal width of string
	 */
	int minWidth;

	public GroupNameBuilder(int index) {
		this.index = index;
	}

	/**
	 * Constructor.
	 * 
	 * @param index    of group
	 * @param padding  padding
	 * @param minWidth width of string
	 */
	public GroupNameBuilder(int index, char padding, int minWidth) {
		this.index = index;
		this.padding = padding;
		this.minWidth = minWidth;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		Objects.requireNonNull(sb);
		Objects.requireNonNull(result);

		
		String value = result.group(index);
		if (value.length() < minWidth) {
			value = String.valueOf(padding).repeat(-value.length() + minWidth) + value;
		}

		sb.append(value);
	}

}
