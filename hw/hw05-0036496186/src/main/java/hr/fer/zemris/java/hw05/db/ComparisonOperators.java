package hr.fer.zemris.java.hw05.db;

/**
 * Provides some implementations of {@link IComparisonOperator} interface for
 * working with strings
 * 
 * @author Matej Fureš
 *
 */
public class ComparisonOperators {
	/**
	 * Compares two strings and returns true if first is smaller in value than
	 * second
	 */
	public static final IComparisonOperator LESS = (x, y) -> x.compareTo(y) < 0;

	/**
	 * Compares two strings and returns true if first is smaller or equal in value
	 * to second
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (x, y) -> x.compareTo(y) <= 0;

	/**
	 * Compares two strings and returns true if first is greater in value than
	 * second
	 */
	public static final IComparisonOperator GREATER = (x, y) -> x.compareTo(y) > 0;

	/**
	 * Compares two strings and returns true if first is greater or equal in value
	 * to second
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (x, y) -> x.compareTo(y) >= 0;

	/**
	 * Compares two strings and returns true if first is equal in value to second
	 */
	public static final IComparisonOperator EQUALS = (x, y) -> x.compareTo(y) == 0;

	/**
	 * Compares two strings and returns true if first is equal in value to second
	 */
	public static final IComparisonOperator NOT_EQUALS = (x, y) -> x.compareTo(y) != 0;

	/**
	 * Compares two strings and returns true if they are alike like described in
	 * assignment
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {
			if (value2.contains("*")) {
				if (value2.charAt(0) == '*') {
					String pattern = value2.substring(1);

					if (isShorterThan(value1, pattern)) {
						return false;
					}

					if (value1.substring(0, pattern.length()).equals(pattern)) {
						return true;
					}

					if (isShorterThan(value1, value2)) {
						return false;
					}

					return value1.substring(1, value2.length()).equals(pattern);
				}

				if (value2.charAt(value2.length() - 1) == '*') {
					value2 = value2.substring(0, value2.length() - 1);
				} else {
					String[] parts = value2.split("\\*");

					if (value1.length() < value2.length() - 1) {
						return false;
					}

					if (value1.subSequence(0, parts[0].length()).equals(parts[0]) && value1
							.subSequence(parts[0].length(), parts[0].length() + parts[1].length()).equals(parts[1])) {
						return true;
					}

					if (value1.length() < value2.length()) {
						return false;
					}

					return value1.subSequence(0, parts[0].length()).equals(parts[0])&& value1.subSequence(parts[0].length() + 1, parts[0].length() + parts[1].length() + 1).equals(parts[1]);
				}
			}

			if (isShorterThan(value1, value2)) {
				return false;
			}

			return value2.equals(value1.substring(0, value2.length()));
		}
	};

	/**
	 * Compares length of 2 strings and returns true if value2 is longer than value1
	 * 
	 * @param value1 first string
	 * @param value2 second string
	 * @return true if value2 is longer than value1
	 */
	private static boolean isShorterThan(String value1, String value2) {
		if (value2.length() > value1.length()) {
			return true;
		}

		return false;
	}
}

