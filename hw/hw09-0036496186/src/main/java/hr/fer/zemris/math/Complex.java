package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines an unchangeable complex number. Two complex numbers are considered
 * equal if their imaginary and real parts don't differ for more than 10e-8
 * 
 * @author matfures
 *
 */
public class Complex {
	/**
	 * Complex number representing 0,0
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Complex number representing 1,0
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Complex number representing -1,0
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Complex number representing 0,1
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Complex number representing 0,-1
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Precision for equals
	 */
	private static final double PRECISION = 10e-8;

	/**
	 * Parts of complex number
	 */
	private double re, im;

	/**
	 * Empty constructor, values are set to 0,0
	 */
	public Complex() {
	}

	/**
	 * Constructor
	 * 
	 * @param re real part of complex number
	 * @param im imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Returns module of complex number
	 * 
	 * @return module of number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Multiplies this complex number to given complex number, and returns result as
	 * new complex number. C can't be null.
	 * 
	 * @param c other operand
	 * @return complex number result
	 * @throws NullPointerException if c is null
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(this.re * c.getRe() - c.getIm() * this.im, this.re * c.getIm() + c.getRe() * this.im);
	}

	/**
	 * Divides this complex number to given complex number, and returns result as
	 * new complex number. C can't be null.
	 * 
	 * @param c other operand
	 * @return complex number result
	 * @throws NullPointerException if c is null
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(
				((this.re * c.getRe() + c.getIm() * this.im)) / (c.getRe() * c.getRe() + c.getIm() * c.getIm()),
				((c.getRe() * this.im - this.re * c.getIm()) / ((c.getRe() * c.getRe() + c.getIm() * c.getIm()))));
	}

	/**
	 * Adds this complex number to given complex number, and returns result as new
	 * complex number. C can't be null.
	 * 
	 * @param c other operand
	 * @return complex number result
	 * @throws NullPointerException if c is null
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(re + c.getRe(), im + c.getIm());
	}

	/**
	 * Subs this complex number to given complex number, and returns result as new
	 * complex number. C can't be null.
	 * 
	 * @param c other operand
	 * @return complex number result
	 * @throws NullPointerException if c is null
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);

		return new Complex(re - c.getRe(), im - c.getIm());
	}

	/**
	 * Returns this number as negated
	 * 
	 * @return this number negated
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Calculates nth power of complex number and returns it. N must be non-negative
	 * 
	 * @param n power
	 * @return result complex number
	 * @throws IllegalArgumentException if n is invalid
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power needs to be non negative.");
		}

		double module = module();
		double angle = getAngle();

		module = Math.pow(module, n);
		angle *= n;

		return new Complex(module * Math.cos(angle), module * Math.sin(angle));
	}

	/**
	 * Calculates n roots of complex number. N must be positive integer
	 * 
	 * @param n root
	 * @return list of roots
	 * @throws IllegalArgumentException if n is invalid
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Power needs to be non negative.");
		}

		List<Complex> list = new ArrayList<Complex>(n);

		double module = Math.pow(module(), 1.0 / n);
		double angle = getAngle();

		for (int i = 0; i < n; i++) {
			list.add(new Complex(module * Math.cos((angle + Math.PI * 2 * i) / n),
					module * Math.sin((angle + Math.PI * 2 * i) / n)));
		}

		return list;
	}

	@Override
	public String toString() {
		return "(" + re + (im < 0 ? "-i" : "+i") + Math.abs(im) + ")";
	}

	/**
	 * Returns value of real part
	 * 
	 * @return real
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Returns value of imaginary part
	 * 
	 * @return im
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Calculates value of angle for given complex number
	 * 
	 * @return angle of complex number
	 */
	private double getAngle() {
		return Math.atan2(im, re);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Math.abs((im - other.getIm())) > PRECISION)
			return false;
		if (Math.abs((re - other.getRe())) > PRECISION)
			return false;
		return true;
	}
}
