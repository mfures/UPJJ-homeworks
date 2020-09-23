package hr.fer.zemris.java.hw17.jvdraw.drawing.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Line;

/**
 * Used for painting components
 * 
 * @author mfures
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * Used for drawing
	 */
	private Graphics2D g2d;

	/**
	 * Constructor
	 * 
	 * @param g2d to be set
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getStart().x - circle.getRadius(), circle.getStart().y - circle.getRadius(),
				circle.getRadius() * 2, circle.getRadius() * 2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int d = filledCircle.getRadius() * 2;
		int x = filledCircle.getStart().x - filledCircle.getRadius();
		int y = filledCircle.getStart().y - filledCircle.getRadius();

		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(x, y, d, d);

		g2d.setColor(filledCircle.getColor());
		g2d.drawOval(x, y, d, d);
	}

}
