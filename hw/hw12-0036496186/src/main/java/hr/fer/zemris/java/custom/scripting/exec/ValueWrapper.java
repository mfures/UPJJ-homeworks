package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Wrapper that wraps all classes of objects and provides some arithmetic
 * operations over it
 * 
 * @author Matej Fureš
 *
 */
public class ValueWrapper {
	/**
	 * Wrapped value
	 */
	private Object value;

	/**
	 * Constructor
	 * 
	 * @param value to be set
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Getter for value
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for value
	 * 
	 * @param value to be set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds 2 values if they are numbers
	 * 
	 * @param incValue to be incremented
	 * @throws RuntimeException if something goes wrong
	 */
	public void add(Object incValue) {
		this.value = execute(this.value, incValue, (x, y) -> x + y, (x, y) -> x + y);
	}

	/**
	 * Subs 2 values if they are numbers
	 * 
	 * @param decValue to be subtracted
	 * @throws RuntimeException if something goes wrong
	 */
	public void subtract(Object decValue) {
		this.value = execute(this.value, decValue, (x, y) -> x - y, (x, y) -> x - y);
	}

	/**
	 * Multiplies 2 values if they are numbers
	 * 
	 * @param mulValue to be multiplied with
	 * @throws RuntimeException if something goes wrong
	 */
	public void multiply(Object mulValue) {
		this.value = execute(this.value, mulValue, (x, y) -> x * y, (x, y) -> x * y);
	}

	/**
	 * Divides 2 values if they are numbers
	 * 
	 * @param divValue to be multiplied with
	 * @throws RuntimeException if something goes wrong
	 */
	public void divide(Object divValue) {
		this.value = execute(this.value, divValue, (x, y) -> x / y, (x, y) -> x / y);
	}

	/**
	 * Compares 2 values if they are numbers
	 * 
	 * @param withValue to be compared with
	 * @throws RuntimeException if something goes wrong
	 */
	public int numCompare(Object withValue) {
		Object sol = execute(this.value, withValue, (x, y) -> Double.valueOf(Double.compare(x, y)),
				(x, y) -> Integer.valueOf(Integer.compare(x, y)));

		return sol instanceof Integer ? ((Integer) sol).intValue() : ((Double) sol).intValue();
	}

	/**
	 * Executes correct function on given arguments
	 * 
	 * @param first       first operand
	 * @param second      second operand
	 * @param functDouble function used if one of arguments is double
	 * @param functInt    function used if both arguments are integers
	 * @return object resulted from function
	 * @throws RuntimeException if something isn't correctly done
	 */
	private Object execute(Object first, Object second, BiFunction<Double, Double, Double> functDouble,
			BiFunction<Integer, Integer, Integer> functInt) {

		if ((first instanceof Integer) || first == null) {
			first = first == null ? Integer.valueOf(0) : first;
			return executeInt((Integer) first, second, functDouble, functInt);
		} else if (first instanceof Double) {
			return executeDouble((Double) first, second, functDouble);
		} else if (first instanceof String) {
			String f = (String) first;
			if (f.contains("E") || f.contains(".")) {
				try {
					return executeDouble(Double.parseDouble(f), second, functDouble);
				} catch (Exception e) {
					throw new RuntimeException("String wasn't in number format");
				}
			} else {
				try {
					return executeInt(Integer.parseInt(f), second, functDouble, functInt);
				} catch (Exception e) {
					throw new RuntimeException("String wasn't in number format");
				}
			}
		}

		throw new RuntimeException("Unsupported class,please use: String,Double or Integer");
	}

	/**
	 * Executes function if first argument is double
	 * 
	 * @param first       first operand
	 * @param second      second operand
	 * @param functDouble function used if second argument is double
	 * @return object resulted from function
	 * @throws RuntimeException if something isn't correctly done
	 */
	private Object executeDouble(Double first, Object second, BiFunction<Double, Double, Double> functDouble) {
		if ((second instanceof Integer) || (second instanceof Double) || second == null) {
			second = second == null ? Double.valueOf(0) : second;
			second = second instanceof Integer ? Double.valueOf(((Integer) second).intValue()) : second;
			return functDouble.apply(first, ((Double) second).doubleValue());
		} else if (second instanceof String) {
			try {
				return functDouble.apply(first, Double.parseDouble((String) second));
			} catch (Exception e) {
				throw new RuntimeException("String wasn't in number format");
			}
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * Executes correct function if first argument is integer
	 * 
	 * @param first       first operand
	 * @param second      second operand
	 * @param functDouble function used if second argument is double
	 * @param functInt    function used if second argument is integers
	 * @return object resulted from function
	 * @throws RuntimeException if something isn't correctly done
	 */
	private Object executeInt(Integer first, Object second, BiFunction<Double, Double, Double> functDouble,
			BiFunction<Integer, Integer, Integer> functInt) {
		if ((second instanceof Integer) || second == null) {
			second = second == null ? Integer.valueOf(0) : second;
			return functInt.apply(((Integer) first).intValue(), ((Integer) second).intValue());
		} else if (second instanceof Double) {
			return functDouble.apply(Double.valueOf(first), ((Double) second).doubleValue());
		} else if (second instanceof String) {
			String s = (String) second;
			if (s.contains("E") || s.contains(".")) {
				try {
					return functDouble.apply(Double.valueOf(first), Double.parseDouble(s));
				} catch (Exception e) {
					throw new RuntimeException("String wasn't in number format");
				}
			} else {
				try {
					return functInt.apply(((Integer) first).intValue(), Integer.parseInt(s));
				} catch (Exception e) {
					throw new RuntimeException("String wasn't in number format");
				}
			}
		} else
			throw new RuntimeException();
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
