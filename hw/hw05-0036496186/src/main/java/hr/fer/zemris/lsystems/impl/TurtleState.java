package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * Defines Turtle state, its position and orientation, also its color and
 * walking distance.
 * 
 * @author Matej Fureš
 *
 */
public class TurtleState {
	/**
	 * Turtles position
	 */
	private Vector2D position;

	/**
	 * Turtles orientation
	 */
	private Vector2D orientation;

	/**
	 * Color used for drawing
	 */
	private Color drawingColor;

	/**
	 * Scale used to scale moves distance
	 */
	private double movesDistanceScale;//Može negativno?

	/**
	 * Creates TurtleState state with given values. Position, orientation and
	 * drawingColor musn't be null, an exception is thrown otherwise
	 * 
	 * @param position           turtles position
	 * @param orientation        turtles orientation
	 * @param drawingColor       turtles drawing Color
	 * @param movesDistanceScale turtles moves distance scale
	 * @throws NullPointerException if position, orientation or drawingColor are
	 *                              null
	 */
	public TurtleState(Vector2D position, Vector2D orientation, Color drawingColor, double movesDistanceScale) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(orientation);
		Objects.requireNonNull(drawingColor);

		this.position = position;
		this.orientation = orientation;
		this.drawingColor = drawingColor;
		this.movesDistanceScale = movesDistanceScale;
	}

	/**
	 * Returns new TurtleState identical to this state
	 * 
	 * @return copy of this state
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), orientation.copy(), drawingColor, movesDistanceScale);
	}

	/**
	 * Method returns turtles position
	 * 
	 * @return turtles position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Position to be set, can't be null
	 * 
	 * @param position to be set
	 * @throws NullPointerException if position is null
	 */
	public void setPosition(Vector2D position) {
		Objects.requireNonNull(position);

		this.position = position;
	}

	/**
	 * Method returns turtles orientation
	 * 
	 * @return turtles orientation
	 */
	public Vector2D getOrientation() {
		return orientation;
	}

	/**
	 * Orientation to be set, can't be null
	 * 
	 * @param orientation to be set
	 * @throws NullPointerException if orientation is null
	 */
	public void setOrientation(Vector2D orientation) {
		Objects.requireNonNull(orientation);

		this.orientation = orientation;
	}

	/**
	 * Method returns turtles drawing color
	 * 
	 * @return turtles drawing color
	 */
	public Color getDrawingColor() {
		return drawingColor;
	}

	/**
	 * DrawingColor to be set, can't be null
	 * 
	 * @param drawingColor to be set
	 * @throws NullPointerException if drawingColor is null
	 */
	public void setDrawingColor(Color drawingColor) {
		Objects.requireNonNull(drawingColor);

		this.drawingColor = drawingColor;
	}

	/**
	 * Method returns turtles moves sistance scale
	 * 
	 * @return turtles moves distance scale
	 */
	public double getMovesDistanceScale() {
		return movesDistanceScale;
	}

	/**
	 * movesDistanceScale to be set
	 * 
	 * @param movesDistanceScale to be set
	 */
	public void setMovesDistanceScale(double movesDistanceScale) {
		this.movesDistanceScale = movesDistanceScale;
	}

}
