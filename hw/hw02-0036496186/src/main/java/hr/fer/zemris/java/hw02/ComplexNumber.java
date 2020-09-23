package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * Provides support for working with complex numbers. Complex number is defined
 * as (a+bi), where both a and b are of type double. Two ComplexNumbers are
 * considered equal if their imaginary parts don't differ for more than 10e-6
 * and if their imaginary parts don't differ for more than 10e-6.
 * 
 * @author Matej Fureš
 *
 */
public class ComplexNumber {
	/**
	 * Stores real part of complex number
	 */
	private double real;

	/**
	 * Stores imaginary part of complex number
	 */
	private double imaginary;

	/**
	 * Stores value of angle for complex number
	 */
	private double angle;

	/**
	 * Stores value of magnitude for complex number
	 */
	private double magnitude;

	/**
	 * Value of precision used to determine if numbers are equal
	 */
	public static double PRECISIONFORDOUBLE = 10e-6;

	/**
	 * Constructor takes two numbers of type double and returns a complex number
	 * with given values for both real and imaginary part, respectively. This
	 * constructor won't accept null values.
	 * 
	 * @param real      real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		setAngle();
		setMagnitude();
	}

	/**
	 * Constructor takes four numbers of type double and returns a complex number
	 * with given values for magnitude, angle, real part and imaginary part of
	 * complex number.
	 * 
	 * @param magnitude value of magnitude for complex number
	 * @param angle     value of angle for complex number
	 * @param real      real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	private ComplexNumber(double real, double imaginary, double magnitude, double angle) {
		this.real = real;
		this.imaginary = imaginary;
		this.angle = angle;
		this.magnitude = magnitude;
	}

	/**
	 * Returns real part of given complex number
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of given complex number
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns value of angle for given complex number
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		return this.angle;
	}

	/**
	 * Calculates value of angle for given complex number for all types of complex
	 * numbers and sets value of angle to it.
	 * 
	 * @return angle of complex number
	 */
	private void setAngle() {
		double tmpAngle = Math.atan2(imaginary, real);

		if (tmpAngle < 0) {
			tmpAngle += Math.PI * 2;
		}

		angle = tmpAngle;
	}

	/**
	 * Returns value of magnitude for given complex number
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return this.magnitude;
	}

	/**
	 * Calculates value of magnitude for given complex number for all types of
	 * complex numbers and sets value of magnitude to it.
	 */
	private void setMagnitude() {
		this.magnitude = Math.sqrt(imaginary * imaginary + real * real);
	}

	/**
	 * Returns new ComplexNumber object that has real part set to value of real.
	 * 
	 * @param real real part of complex number
	 * @return Complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns new ComplexNumber object that has imaginary part set to value of
	 * imaginiary.
	 * 
	 * @param real real part of complex number
	 * @return Complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns new ComplexNumber object that has imaginary and real part set to
	 * values that are calculated from angle and magnitude.
	 * 
	 * @param magnitude value of magnitude for given complex number
	 * @param angle     value of angle for given complex number
	 * @return complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude, magnitude, angle);
	}

	/**
	 * Provides parsing operation over given string to create a ComplexNumber. First
	 * found number gets multiplied by leadingSing. Successively parses strings of
	 * format "a+bi", "a-bi", "+a+bi" and "-a-bi" where a and b are parsable by
	 * Double.parseDouble(). For incorrect input, throws exception. If s is null,
	 * throws exception.
	 * 
	 * @param s String to be parsed
	 * @return ComplexNumber parsed from string
	 * @throws NullPointerException if s== null
	 * @throws neka                 if s isn't in good format
	 */
	public static ComplexNumber parse(String s) {
		Objects.requireNonNull(s);
		if (s.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (s.charAt(0) == '+') {
			if (s.length() == 1 || s.charAt(1) == '+' || s.charAt(1) == '-') {
				throw new IllegalArgumentException();
			}
			return parser(s.substring(1), 1);
		}

		if (s.charAt(0) == '-') {
			if (s.length() == 1 || s.charAt(1) == '+' || s.charAt(1) == '-') {
				throw new IllegalArgumentException();
			}
			return parser(s.substring(1), -1);
		}

		return parser(s, 1);
	}

	/**
	 * Provides parsing operation over given string to create a ComplexNumber. First
	 * found number gets multiplied by leadingSing. Successively parses strings of
	 * format "a+bi" and "a-bi" where a and b are parsable by Double.parseDouble().
	 * For incorrect input, throws exception
	 * 
	 * @param s           String to be parsed
	 * @param leadingSign sign of first found number
	 * @return ComplexNumber parsed from s
	 * @throws IllegalArgumentException for incorrect string format
	 */
	private static ComplexNumber parser(String s, int leadingSign) {
		if (s.charAt(s.length() - 1) != 'i') {
			try {
				return fromReal(leadingSign * Double.parseDouble(s));
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		} else {
			if (s.split("\\+").length == 1) {
				if (s.split("-").length == 1) {
					try {
						return fromImaginary(leadingSign * (s.substring(0, s.length() - 1).isEmpty() ? 1
								: Double.parseDouble(s.substring(0, s.length() - 1))));
					} catch (Exception e) {
						throw new IllegalArgumentException();
					}
				} else {
					if (s.split("-").length != 2 || s.split("-")[1].charAt(0) == '+'
							|| s.split("-")[1].charAt(0) == '-') {
						throw new IllegalArgumentException();
					} else {
						try {
							return new ComplexNumber(
									leadingSign * Double.parseDouble(s.substring(0, s.length() - 1).split("-")[0]),
									-(s.split("-")[1].length() == 1 ? 1
											: Double.parseDouble(s.substring(0, s.length() - 1).split("-")[1])));
						} catch (Exception e) {
							throw new IllegalArgumentException();
						}
					}
				}
			} else {
				if (s.split("\\+")[1].charAt(0) == '+' || s.split("\\+")[1].charAt(0) == '-') {
					throw new IllegalArgumentException();
				}
				try {
					return new ComplexNumber(
							leadingSign * Double.parseDouble(s.substring(0, s.length() - 1).split("\\+")[0]),
							s.split("\\+")[1].length() == 1 ? 1
									: Double.parseDouble(s.substring(0, s.length() - 1).split("\\+")[1]));
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			}
		}
	}

	/**
	 * Returns String value of current number in format "a+bi"
	 */
	@Override
	public String toString() {
		if (real == 0) {
			if (imaginary == 0) {
				return "0";
			} else {
				return String.valueOf(imaginary) + "i";
			}
		} else {
			if (imaginary == 0) {
				return String.valueOf(real);
			}
		}
		String tmp = String.valueOf(real);
		tmp += (imaginary > 0) ? "+" : "";
		return tmp + String.valueOf(imaginary) + "i";
	}

	/**
	 * Does addition over two complex number. The result is returned as new
	 * ComplexNumber. Operators don't change by this operation. If operator is null,
	 * an exception is thrown.
	 * 
	 * @param number ComplexNumber to be added
	 * @return ComplexNumber that is the result of the operation
	 * @throws NullPointerException if operator is null
	 */
	public ComplexNumber add(ComplexNumber number) {
		if (number == null) {
			throw new NullPointerException();
		}

		return new ComplexNumber(this.real + number.getReal(), this.imaginary + number.getImaginary());
	}

	/**
	 * Does subtraction over two complex number. The result is returned as new
	 * ComplexNumber. Operators don't change by this operation. If operator is null,
	 * an exception is thrown.
	 * 
	 * @param number ComplexNumber to be subtracted
	 * @return ComplexNumber that is the result of the operation
	 * @throws NullPointerException if operator is null
	 */
	public ComplexNumber sub(ComplexNumber number) {
		if (number == null) {
			throw new NullPointerException();
		}

		return new ComplexNumber(this.real - number.getReal(), this.imaginary - number.getImaginary());
	}

	/**
	 * Does multiplication over two complex number. The result is returned as new
	 * ComplexNumber. Operators don't change by this operation. If operator is null,
	 * an exception is thrown.
	 * 
	 * @param number ComplexNumber to be multiplied by
	 * @return ComplexNumber that is the result of the operation
	 * @throws NullPointerException if operator is null
	 */
	public ComplexNumber mul(ComplexNumber number) {
		if (number == null) {
			throw new NullPointerException();
		}

		return new ComplexNumber(this.real * number.getReal() - number.getImaginary() * this.imaginary,
				this.real * number.getImaginary() + number.getReal() * this.imaginary);
	}

	/**
	 * Method divides complex number with the complex number give as the argument
	 * and returns new ComplexNumber with calculated values. This operation doesn't
	 * change this ComplexNumbers values.
	 * 
	 * @param number ComplexNumber used to divide
	 * @return new ComplexNumber with calculated values
	 * @throws NullPointerException if given argument is null
	 */
	public ComplexNumber div(ComplexNumber number) {
		if (number == null) {
			throw new NullPointerException();
		}

		return new ComplexNumber(
				((this.real * number.getReal() + number.getImaginary() * this.imaginary))
						/ (number.getReal() * number.getReal() + number.getImaginary() * number.getImaginary()),
				((number.getReal() * this.imaginary - this.real * number.getImaginary())
						/ ((number.getReal() * number.getReal() + number.getImaginary() * number.getImaginary()))));
	}

	/**
	 * Returns new ComplexNumber that is equal to current power raised to power n.
	 * This operation doesn't change current complex number. If value of n is less
	 * than 0, throws exception.
	 * 
	 * @param n value of power
	 * @return new ComplexNumber calculated
	 * @throws IllegalArgumentException for n<0
	 */

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		return fromMagnitudeAndAngle(Math.pow(magnitude, n), angle * n);
	}

	/**
	 * Returns new ComplexNumber array that all n roots of ComplexNumber. This
	 * operation doesn't change current complex number. If value of n is less than
	 * 0, throws exception.
	 * 
	 * @param n value of root
	 * @return new ComplexNumber array calculated
	 * @throws IllegalArgumentException for n<1
	 */

	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		ComplexNumber[] roots = new ComplexNumber[n];
		double newMagnitude = Math.pow(magnitude, 1.0 / n);
		for (int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(newMagnitude, (angle + Math.PI * 2 * i) / n);
		}

		return roots;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Objects are equal
	 * if their imaginary parts don't differ for more than 10e-6 and if their
	 * imaginary parts don't differ for more than 10e-6.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (imaginary - other.imaginary > PRECISIONFORDOUBLE)
			return false;
		if (real - other.real > PRECISIONFORDOUBLE)
			return false;
		return true;
	}
}
