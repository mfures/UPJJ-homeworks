package hr.fer.zemris.java.hw17.jvdraw.drawing.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.collor.util.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.Tool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.JDrawingCanvas;

/**
 * TOol used for drwaing filled circles
 * 
 * @author mfures
 *
 */
public class FilledCircleTool implements Tool {
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
	 * Background color provider
	 */
	private IColorProvider bgprovider;

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
	 * @param provider   for color
	 * @param bgprovider for color
	 * @param canvas     for repainting
	 * @param model      to hold objects
	 */
	public FilledCircleTool(IColorProvider provider, IColorProvider bgprovider, JDrawingCanvas canvas,
			DrawingModel model) {
		this.provider = provider;
		this.bgprovider = bgprovider;
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
			model.add(new FilledCircle(center, end, provider.getCurrentColor(), bgprovider.getCurrentColor()));
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
			g2d.setColor(bgprovider.getCurrentColor());
			g2d.fillOval(center.x - r, center.y - r, 2 * r, 2 * r);
			g2d.setColor(provider.getCurrentColor());
			g2d.drawOval(center.x - r, center.y - r, 2 * r, 2 * r);
		}
	}
}
