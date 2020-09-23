package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This is a command-line application which accepts a single command-line
 * argument: expression which should be evaluated. Expression must be in postfix
 * representation.
 * 
 * @author Matej Fureš
 *
 */
public class StackDemo {
	/**
	 * Shuts down the program if incorrect number of arguments is sent. Otherwise
	 * sends the expression (split by spaces)to be evaluated.
	 * 
	 * @param args should be 1 command-line argument
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Inncorect number of arguments!");
			return;
		}

		try {
			System.out.println(solveExpression(args[0].trim().split("\\s+")));
		} catch (EmptyStackException e) {
			System.out.println("Argument isnt in postfix notation!");
		} catch (IllegalArgumentException e) {
			System.out.println("You tryed to do an unsuported action!");
		} catch (ArithmeticException e) {
			System.out.println("You tried to either dived by or do a mod with 0!");
		}
	}

	/**
	 * Solves an expression in postfix notation. This method accepts only integer
	 * operations and data type. Supported operations are: +, -, /, *, %. Throws an
	 * exception if operation is % or /, and second operator is 0. Throws an
	 * exception if operation is unsupported. Throws an EmptyStackException if stack
	 * becomes empty during algorithm
	 * 
	 * @param strings array to be evaluated
	 * @return result of postfix expression
	 * @throws EmptyStackException      if stack becomes empty during algorithm
	 * @throws IllegalArgumentException if operation is unsupported
	 * @throws ArithmeticException      if is tried to dived by 0, or to do mod by 0
	 */
	private static int solveExpression(String... strings) {
		ObjectStack stack = new ObjectStack();
		for (String s : strings) {
			try {
				stack.push(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				int first = (int) stack.pop();
				int second = (int) stack.pop();
				stack.push(operationManager(second, first, s));
			}
		}
		return (int) stack.pop();
	}

	/**
	 * Does an operation over two numbers in format: first(operation)second.
	 * Supported operations are: +, -, /, *, %. Throws an exception if operation is
	 * % or /, and second operator is 0. Throws an exception if operation is
	 * unsupported.
	 * 
	 * @param first     first operator
	 * @param second    second operator
	 * @param operation operation to be done over operators first(operation)second
	 * @return (first)operation(second)
	 * @throws IllegalArgumentException if operation is unsupported
	 */

	private static int operationManager(int first, int second, String operation) {
		switch (operation) {
		case "+":
			return first + second;
		case "-":
			return first - second;
		case "/":
			isArgumentZero(second);
			return first / second;
		case "*":
			return first * second;
		case "%":
			isArgumentZero(second);
			return first % second;
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Throws {@link ArithmeticException} if n == 0
	 * 
	 * @param n number to be checked
	 * @throws ArithmeticException if n is 0.
	 */
	private static void isArgumentZero(int n) {
		if (n == 0) {
			throw new ArithmeticException();
		}
	}
}
