package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.editors.LineEditor;

public class Line extends GeometricalObject {
	/**
	 * Start and end pont
	 */
	private Point start, end;

	/**
	 * Color of line
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param start point
	 * @param end   point
	 * @param color of line
	 */
	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
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
	 * Called when state of object changes
	 */
	private void stateChanged() {
		listeners.forEach(x -> x.geometricalObjectChanged(this));
	}

	/**
	 * Setter for start
	 * 
	 * @param start to be set
	 */
	public void setStart(Point start) {
		this.start = start;
		stateChanged();
	}

	/**
	 * Setter for end
	 * 
	 * @param end to be set
	 */
	public void setEnd(Point end) {
		this.end = end;
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
	 * Parses line to {@link Line}
	 * 
	 * @param line to be parsed
	 * @return new {@link Line}
	 * @throws RuntimeException if line can't be parsed
	 */
	public static Line parse(String line) {
		try {
			String[] arr = line.split("\\s");
			int sX = Integer.valueOf(arr[1]);
			int sY = Integer.valueOf(arr[2]);
			int eX = Integer.valueOf(arr[3]);
			int eY = Integer.valueOf(arr[4]);
			int r = Integer.valueOf(arr[5]);
			int g = Integer.valueOf(arr[6]);
			int b = Integer.valueOf(arr[7]);
			return new Line(new Point(sX, sY), new Point(eX, eY), new Color(r, g, b));
		} catch (Exception e) {
			throw new RuntimeException("Couldn't parse line");
		}
	}

	/**
	 * Text in format for writing
	 * 
	 * @return text
	 */
	public String write() {
		return String.format("LINE %d %d %d %d %d %d %d\r\n", start.x, start.y, end.x, end.y, color.getRed(),
				color.getGreen(), color.getBlue());
	}

	/**
	 * Line in format for JList
	 * 
	 * @return string representation of line
	 */
	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", start.x, start.y, end.x, end.y);
	}
}
