package hr.fer.zemris.java.hw17.jvdraw.drawing.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.drawing.Tool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.visitors.GeometricalObjectPainter;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	private static final long serialVersionUID = -6219734175161683461L;

	/**
	 * Drawing model
	 */
	private DrawingModel model;

	/**
	 * Supplier of tool
	 */
	private Supplier<Tool> supplier;

	/**
	 * Constructor
	 * 
	 * @param model    drawing model
	 * @param supplier of tools
	 */
	public JDrawingCanvas(DrawingModel model, Supplier<Tool> supplier) {
		this.model = model;
		model.addDrawingModelListener(this);
		this.supplier = supplier;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				supplier.get().mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				supplier.get().mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				supplier.get().mouseReleased(e);
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				supplier.get().mouseMoved(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				supplier.get().mouseDragged(e);
			}
		});
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		supplier.get().paint((Graphics2D) getGraphics());
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		supplier.get().paint((Graphics2D) getGraphics());
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		supplier.get().paint((Graphics2D) getGraphics());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);

		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}

		supplier.get().paint(g2d);
	}
}
