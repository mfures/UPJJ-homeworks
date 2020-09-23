package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a complex polynomial
 * 
 * @author matfures
 *
 */
public class ComplexPolynomial {
	/**
	 * Array that stores coefficients of polynomial
	 */
	private Complex[] coefficients;

	/**
	 * Creates polynomial with given factors. Factors can't be null and can't be
	 * empty
	 * 
	 * @param factors of polynomial
	 * @throws NullPointerException     if factors are null
	 * @throws IllegalArgumentException if factors length is 0
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);

		if (factors.length == 0) {
			throw new IllegalArgumentException("Factors must contain atleast 1 complex number");
		}

		if (factors.length > Short.MAX_VALUE) {
			throw new IllegalArgumentException("Factors must contain atmost " + Short.MAX_VALUE + " complex numbers");
		}

		int order = -1;
		for (int i = factors.length - 1; i >= 0; i--) {
			if (factors[i].getRe() == 0 && factors[i].getIm() == 0) {
				continue;
			}

			order = i;
			break;
		}

		if (order == -1) {
			coefficients = new Complex[] { Complex.ZERO };
			return;
		}

		coefficients = new Complex[order + 1];

		for (int i = 0; i < order + 1; i++) {
			coefficients[i] = factors[i];
		}
	}

	/**
	 * Returns order of polynomial
	 * 
	 * @return order of polynomial
	 */
	public short order() {
		return (short) (coefficients.length - 1);
	}

	/**
	 * Multiplies polynomials. Other polynomial mustn't be null
	 * 
	 * @param p other polynomial
	 * @return result polynomial
	 * @throws NullPointerException if p is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);

		int orderOfFirst = order(), orderOfSecond = p.order();
		int maxOrder = orderOfFirst + orderOfSecond;

		Complex[] factors = new Complex[maxOrder + 1];
		Complex[] otherCoefficients = p.coefficients;

		for (int i = 0; i <= maxOrder; i++) {
			factors[i] = Complex.ZERO;
		}

		for (int i = 0; i <= orderOfFirst; i++) {
			for (int j = 0; j <= orderOfSecond; j++) {
				factors[i + j] = factors[i + j].add(coefficients[i].multiply(otherCoefficients[j]));
			}
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Returns new Complex number which is derivation of this polynomial
	 * 
	 * @return derived polynomial
	 */
	public ComplexPolynomial derive() {
		if (order() == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}

		Complex[] factors = new Complex[order()];

		for (int i = order(); i > 0; i--) {
			factors[i - 1] = coefficients[i].multiply(new Complex(i, 0));
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Calculates value of polynomial in given number
	 * 
	 * @param z position
	 * @return result
	 * @throws NullPointerException if z is null
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);
		Complex result = Complex.ZERO;

		for (int i = order(); i >= 0; i--) {
			result = result.add(coefficients[i].multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		if (order() == 0) {
			return coefficients[0].toString();
		}

		StringBuilder sb = new StringBuilder();

		for (int i = order(); i > 0; i--) {
			sb.append(coefficients[i].toString());
			sb.append("*(z^" + i + ")+");
		}

		sb.append(coefficients[0].toString());
		return sb.toString();
	}

	/**
	 * Returns copy of coefficients
	 * 
	 * @return copy of Coefficients
	 */
	public Complex[] getCoefficients() {
		return Arrays.copyOf(coefficients, coefficients.length);
	}
}
