package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Line;

/**
 * Simple visitor for various {@link GeometricalObject} implementations
 * 
 * @author mfures
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visit method used for line
	 * 
	 * @param line to visit
	 */
	public abstract void visit(Line line);

	/**
	 * Visit method for circle
	 * 
	 * @param circle to visit
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visit method used for filled circle
	 * 
	 * @param filledCircle to visit
	 */
	public abstract void visit(FilledCircle filledCircle);
}
