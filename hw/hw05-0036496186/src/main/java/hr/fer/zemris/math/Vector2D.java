package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Defines a simple two dimensional vector and some operations for working with
 * it
 * 
 * @author Matej Fureš
 *
 */
public class Vector2D {
	/**
	 * Vectors x value
	 */
	private double x;

	/**
	 * Vectors y value
	 */
	private double y;

	/**
	 * Constructor that creates the vector
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x value of vector
	 * 
	 * @return vectors x value
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y value of vector
	 * 
	 * @return vectors y value
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates vector for given offset. Offset vector musn't be null, exception
	 * is thrown otherwise
	 * 
	 * @param offset value for which vector is offset
	 * @throws NullPointerException if offset is null
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);

		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 * Returns new vector that is equal to this vector translated for given offset.
	 * Offset vector musn't be null, exception is thrown otherwise
	 * 
	 * @param offset value for which vector is offset
	 * @throws NullPointerException if offset is null
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset);

		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}

	/**
	 * Rotates vector for given offset.
	 * 
	 * @param angle angle for which vector is rotated
	 */
	public void rotate(double angle) {
		double newX = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
		double newY = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));

		x = newX;
		y = newY;
	}

	/**
	 * Returns new vector that is equal to this vector rotated for given angle.
	 * 
	 * @param angle angle for which vector is rotated
	 */
	public Vector2D rotated(double angle) {
		double newX = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
		double newY = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));

		return new Vector2D(newX, newY);
	}

	/**
	 * Scales vector for given value.
	 * 
	 * @param scaler scaler for which vector is scaled
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Returns new vector that is equal to this vector scaled for given scaler.
	 * 
	 * @param scaler scaler for which vector is scaled
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Returns new vector that is equal to this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

}
