package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Defines an unchangeable 3 dimensional vector
 * 
 * @author matfures
 *
 */
public class Vector3 {
	/**
	 * Coordinates of vector
	 */
	private double x, y, z;

	/**
	 * Constructor
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param z coordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns vectors norm (length)
	 * 
	 * @return vectors norm
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns normalized version of vector
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double n = norm();
		return new Vector3(x / n, y / n, z / n);
	}

	/**
	 * Ads given vector to this vector and returns result as new Vector. Other
	 * vector can't be null
	 * 
	 * @param other to be added
	 * @return result vector
	 * @throws NullPointerException if other is null
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);

		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}

	/**
	 * Subtracts given vector to this vector and returns result as new Vector. Other
	 * vector can't be null
	 * 
	 * @param other to be added
	 * @return result vector
	 * @throws NullPointerException if other is null
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);

		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	/**
	 * Calculates dot product of vectors. Other vector can't be null
	 * 
	 * @param other vector
	 * @return dot product
	 * @throws NullPointerException if other is null
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);

		return x * other.getX() + y * other.getY() + z * other.getZ();
	}

	/**
	 * Calculates cross product of vectors. Other vector can't be null
	 * 
	 * @param other vector
	 * @return dot product
	 * @throws NullPointerException if other is null
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		double newX, newY, newZ;

		newX = y * other.getZ() - z * other.getY();
		newY = z * other.getX() - x * other.getZ();
		newZ = x * other.getY() - y * other.getX();

		return new Vector3(newX, newY, newZ);
	}

	/**
	 * Scales vector with given constant and returns new vector as result
	 * 
	 * @param s scaler
	 * @return result vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Calculates value of cos of angle between this vector and other vector. Other
	 * vector can't be null
	 * 
	 * @param other vector
	 * @return value of cos of angle
	 * @throws NullPointerException if other is null
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);

		return dot(other) / (norm() * other.norm());
	}

	/**
	 * Returns array containing values of coordinates of vector
	 * 
	 * @return array containing coordinates
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		DecimalFormat f=new DecimalFormat("#0.000000");
		
		return "(" + f.format(x) + ", " + f.format(y) + ", " + f.format(z) + ")";
	}

	/**
	 * Returns value of x coordinate
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns value of y coordinate
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns value of z coordinate
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}
}
