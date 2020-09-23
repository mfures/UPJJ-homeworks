package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.editors.CircleEditor;

public class Circle extends GeometricalObject {
	/**
	 * Start and end pont
	 */
	private Point start, end;

	/**
	 * Color of line
	 */
	private Color color;

	/**
	 * Radius
	 */
	private int radius;

	/**
	 * Constructor
	 * 
	 * @param start  point
	 * @param radius radius
	 * @param color  of line
	 */
	public Circle(Point start, int radius, Color color) {
		this.start = start;
		this.color = color;
		this.radius = radius;
	}

	/**
	 * Constructor
	 * 
	 * @param start point
	 * @param end   point
	 * @param color of line
	 */
	public Circle(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.radius = (int) Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * Getter for start
	 * 
	 * @return start
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Getter for end
	 * 
	 * @return end
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Getter for color
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter for radius
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Called when state of object changes
	 */
	private void stateChanged() {
		listeners.forEach(x -> x.geometricalObjectChanged(this));
	}

	/**
	 * Setter for radius
	 * 
	 * @param radius to be set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		this.end = new Point(start);
		end.y += radius;
		stateChanged();
	}

	/**
	 * Setter for start
	 * 
	 * @param start to be set
	 */
	public void setStart(Point start) {
		this.start = start;
		this.radius = (int) Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
		stateChanged();
	}

	/**
	 * Setter for end
	 * 
	 * @param end to be set
	 */
	public void setEnd(Point end) {
		this.end = end;
		this.radius = (int) Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
		stateChanged();
	}

	/**
	 * Setter for color
	 * 
	 * @param color to be set
	 */
	public void setColor(Color color) {
		this.color = color;
		stateChanged();
	}

	/**
	 * Parses line to {@link Circle}
	 * 
	 * @param line to be parsed
	 * @return new {@link Circle}
	 * @throws RuntimeException if line can't be parsed
	 */
	public static Circle parseLine(String line) {
		try {
			String[] arr = line.split("\\s");
			int sX = Integer.valueOf(arr[1]);
			int sY = Integer.valueOf(arr[2]);
			int rad = Integer.valueOf(arr[3]);
			int r = Integer.valueOf(arr[4]);
			int g = Integer.valueOf(arr[5]);
			int b = Integer.valueOf(arr[6]);
			return new Circle(new Point(sX, sY), rad, new Color(r, g, b));
		} catch (Exception e) {
			throw new RuntimeException("Couldn't parse line");
		}
	}

	/**
	 * Creates text for saving
	 * 
	 * @return string for saving
	 */
	public String write() {
		return String.format("CIRCLE %d %d %d %d %d %d\r\n", start.x, start.y, radius, color.getRed(), color.getGreen(),
				color.getBlue());
	}

	/**
	 * Line in format for JList
	 * 
	 * @return string representation of line
	 */
	@Override
	public String toString() {
		return String.format("Circle (%d,%d),%d", start.x, start.y, radius);
	}
}
