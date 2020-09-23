package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Parser for renaming expressions
 * 
 * @author Matej Fureš
 *
 */
public class NameBuilderParser {
	/**
	 * Contains name builders parsed from expression
	 */
	private List<NameBuilder> list;

	/**
	 * Contains input as char array
	 */
	private char[] data;

	/**
	 * Tracker for position in array
	 */
	private int current;

	/**
	 * Constructor. Input mustn't be null
	 * 
	 * @param input to be parsed
	 * @throws NullPointerException if input is null
	 */
	public NameBuilderParser(String input) {
		Objects.requireNonNull(input);

		list = new ArrayList<>();
		parse(input);
	}

	/**
	 * Returns name builder
	 * 
	 * @return name builder
	 */
	public NameBuilder getNameBuilder() {
		return new TopLevelNameBuilder(list);
	}

	private void parse(String input) {
		data = input.toCharArray();
		StringBuilder sb = new StringBuilder();

		if (startOfTag()) {// if starts with tag

		}

		while (current < data.length) {
			if (!startOfTag()) {
				sb.append(data[current]);
				current++;
			} else {
				list.add(new StringNameBuilder(sb.toString()));
				sb.setLength(0);

				current += 2;
				requireTagOpen();

				while (current < data.length) {
					if (Character.isDigit(data[current])) {
						sb.append(data[current]);
						current++;
					} else {
						int groupNum;
						try {
							groupNum = Integer.parseInt(sb.toString());
							sb.setLength(0);
						} catch (Exception e) {
							throw new IllegalArgumentException("Couldn't parse number");
						}

						requireTagOpen();

						if (data[current] == '}') {
							list.add(new GroupNameBuilder(groupNum));
							current++;
							break;
						}

						if (data[current] == ',') {
							current++;
							requireTagOpen();
							char pad = ' ';

							if (!(current < data.length - 1)) {
								throw new IllegalArgumentException("Illegal format in tag");
							}

							if (data[current] == '0' && Character.isDigit(data[current + 1])) {
								pad = '0';
								current++;
							}

							while (current < data.length) {
								if (Character.isDigit(data[current])) {
									sb.append(data[current]);
									current++;
								} else {
									int minLen;
									try {
										minLen = Integer.parseInt(sb.toString());
										sb.setLength(0);
										list.add(new GroupNameBuilder(groupNum, pad, minLen));
									} catch (Exception e) {
										throw new IllegalArgumentException("Couldn't parse number2");
									}

									break;
								}
							}

							if (!(current < data.length)) {
								throw new IllegalArgumentException("Tag not closed");
							}else if (data[current] != '}') {
								throw new IllegalArgumentException("Tag not closed");
							}

							current++;
							break;
						} else {
							throw new IllegalArgumentException("Illegal Argument in tag");
						}
					}
				}
			}
		}
		if(sb.length()!=0) {
			list.add(new StringNameBuilder(sb.toString()));
		}
	}

	/**
	 * Checks if tag is still open, and throws an exception if tag isn't open
	 * anymore
	 * 
	 * @throws IllegalArgumentException if
	 */
	private void requireTagOpen() {
		skipTabsAndSpaces();

		if (current == data.length) {
			throw new IllegalArgumentException("Tag opened but not closed");
		}

	}

	/**
	 * Skips all tabs and spaces
	 */
	private void skipTabsAndSpaces() {
		while (current < data.length) {
			if (data[current] == ' ' || data[current] == '\t') {
				current++;
			} else {
				break;
			}
		}
	}

	/**
	 * Returns true if tag starts on current index
	 * 
	 * @return true if tag starts
	 */
	private boolean startOfTag() {
		if (current < data.length - 1) {
			if (data[current] == '$' && data[current + 1] == '{') {
				return true;
			}
		}

		return false;
	}
}
