package hr.fer.zemris.math;

import java.util.List;
import java.util.Objects;

/**
 * Represents Complex polynomial in root form
 * 
 * @author matfures
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * Constant multiplying polynomial
	 */
	private Complex constant;

	/**
	 * Roots of polynomial
	 */
	private Complex[] roots;

	/**
	 * Creates polynomial with given roots and constant. Arguments can't be null
	 * 
	 * @param roots of polynomial
	 * @throws NullPointerException if roots are null
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		Objects.requireNonNull(constant);
		Objects.requireNonNull(roots);

		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Creates polynomial with given roots list and constant. Arguments can't be
	 * null
	 * 
	 * @param roots of polynomial
	 * @throws NullPointerException if roots are null
	 */
	public ComplexRootedPolynomial(Complex constant, List<Complex> roots) {
		Objects.requireNonNull(constant);
		Objects.requireNonNull(roots);

		this.constant = constant;
		this.roots = new Complex[roots.size()];

		for (int i = 0; i < roots.size(); i++) {
			this.roots[i] = roots.get(i);
		}
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

		Complex result = constant;

		// Opted for calculating value directly instead of using polynomials apply
		// method since this saves time and memory
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	/**
	 * Transforms polynomial into other form
	 * 
	 * @return polynomial in other form
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial p = new ComplexPolynomial(constant);

		for (int i = 0; i < roots.length; i++) {
			p = p.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}

		return p;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());

		for (int i = 0; i < roots.length; i++) {
			sb.append("*(z-" + roots[i].toString() + ")");
		}

		return sb.toString();
	}

	/**
	 * Returns index of closest root index to position z. If there is no such index
	 * '1 is returned. Z mustn't be null and double mustn't be negative.
	 * 
	 * @param z        position
	 * @param treshold for acceptance
	 * @return -1 or index of closest root
	 * @throws NullPointerException     if z is null
	 * @throws IllegalArgumentException if treshold is negative
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		Objects.requireNonNull(z);
		if (treshold < 0) {
			throw new IllegalArgumentException("Treshold must be positive");
		}

		int indexOfClosest = -1;
		double distance;

		for (int i = 0; i < roots.length; i++) {
			if ((distance = z.sub(roots[i]).module()) < treshold) {
				indexOfClosest = i;
				treshold = distance;
			}
		}

		return indexOfClosest;
	}
}
