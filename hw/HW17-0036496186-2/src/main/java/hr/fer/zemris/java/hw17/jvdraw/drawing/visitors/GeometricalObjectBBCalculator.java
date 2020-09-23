package hr.fer.zemris.java.hw17.jvdraw.drawing.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Line;

/**
 * Visitor used for calculating bounding box of object
 * 
 * @author mfures
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * Dimensions of rectangle
	 */
	private int xMax, yMax, xMin, yMin;

	/**
	 * Are dimensions valid
	 */
	private boolean valid;

	@Override
	public void visit(Line line) {
		Point start = line.getStart();
		Point end = line.getEnd();
		if (!valid) {
			valid = true;
			xMax = Math.max(start.x, end.x);
			xMin = Math.min(start.x, end.x);
			yMax = Math.max(start.y, end.y);
			yMin = Math.min(start.y, end.y);
		} else {
			xMax = Math.max(xMax, Math.max(start.x, end.x));
			xMin = Math.min(xMin, Math.min(start.x, end.x));
			yMax = Math.max(yMax, Math.max(start.y, end.y));
			yMin = Math.min(yMin, Math.min(start.y, end.y));
		}
	}

	@Override
	public void visit(Circle circle) {
		visitCircles(circle.getRadius(), circle.getStart());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visitCircles(filledCircle.getRadius(), filledCircle.getStart());
	}

	/**
	 * Used for visiting circles
	 * 
	 * @param r radius
	 * @param c point of center
	 */
	private void visitCircles(int r, Point c) {
		if (!valid) {
			valid = true;
			xMax = c.x + r;
			xMin = c.x - r;
			yMax = c.y + r;
			yMin = c.y - r;
		} else {
			xMax = Math.max(xMax, c.x + r);
			xMin = Math.min(xMin, c.x - r);
			yMax = Math.max(yMax, c.y + r);
			yMin = Math.min(yMin, c.y - r);
		}

	}

	/**
	 * Returns bounding box
	 * 
	 * @return bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
	}

}
