package hr.fer.zemris.java.hw17.jvdraw.drawing.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.collor.util.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.Tool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.JDrawingCanvas;

/**
 * TOol used for drawing circles
 * 
 * @author mfures
 *
 */
public class CircleTool implements Tool {
	/**
	 * Center of circle
	 */
	private Point center;

	/**
	 * End point
	 */
	private Point end;

	/**
	 * Flag for drawing
	 */
	private boolean active;

	/**
	 * Color provider
	 */
	private IColorProvider provider;

	/**
	 * Canvas on which is drawn
	 */
	private JDrawingCanvas canvas;

	/**
	 * MOdel holding the objects
	 */
	private DrawingModel model;

	/**
	 * COnstructor
	 * 
	 * @param provider for color
	 * @param canvas   for repainting
	 * @param model    to hold objects
	 */
	public CircleTool(IColorProvider provider, JDrawingCanvas canvas, DrawingModel model) {
		this.provider = provider;
		this.canvas = canvas;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (active) {
			active = false;
			end = e.getPoint();
			model.add(new Circle(center, end, provider.getCurrentColor()));
		} else {
			active = true;
			center = e.getPoint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (active) {
			end = e.getPoint();
			canvas.repaint();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (active) {
			int r = (int) center.distance(end);
			g2d.setColor(provider.getCurrentColor());
			g2d.drawOval(center.x - r, center.y - r, 2 * r, 2 * r);
		}
	}
}
