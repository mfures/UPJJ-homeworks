package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;

public class CircleEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = -3863102941525871482L;

	/**
	 * Circle
	 */
	private Circle circle;

	/**
	 * Start x
	 */
	private JTextField sX;

	/**
	 * Start y
	 */
	private JTextField sY;

	/**
	 * Radius
	 */
	private JTextField radius;

	/**
	 * Color components
	 */
	private JTextField r, g, b;

	/**
	 * Constructor
	 * 
	 * @param circle to edit
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		sX = new JTextField(String.valueOf(circle.getStart().x),4);
		sY = new JTextField(String.valueOf(circle.getStart().y),4);
		radius = new JTextField(String.valueOf(circle.getRadius()),4);
		r = new JTextField(String.valueOf(circle.getColor().getRed()),4);
		g = new JTextField(String.valueOf(circle.getColor().getGreen()),4);
		b = new JTextField(String.valueOf(circle.getColor().getBlue()),4);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel start = new JPanel();
		start.add(new JLabel("Start x:"));
		start.add(sX);
		start.add(new JLabel("Start y:"));
		start.add(sY);
		add(start);

		JPanel rad = new JPanel();
		rad.add(new JLabel("Radius:"));
		rad.add(radius);
		add(rad);

		JPanel color = new JPanel();
		color.add(new JLabel("RED:"));
		color.add(r);
		color.add(new JLabel("GREEN:"));
		color.add(g);
		color.add(new JLabel("BLUE:"));
		color.add(b);
		add(color);
	}

	@Override
	public void checkEditing() {
		int rc, gc, bc, rad;
		try {
			Integer.valueOf(sX.getText());
			Integer.valueOf(sY.getText());
			rad = Integer.valueOf(radius.getText());
			rc = Integer.valueOf(r.getText());
			gc = Integer.valueOf(g.getText());
			bc = Integer.valueOf(b.getText());
		} catch (Exception e) {
			throw new RuntimeException("Invalid values");
		}
		if (rc < 0 || rc > 255 || gc < 0 || gc > 255 || bc < 0 || bc > 255) {
			throw new RuntimeException("Invalid color values");
		}
		if (rad < 0) {
			throw new RuntimeException("Negative radius");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setStart(new Point(Integer.valueOf(sX.getText()), Integer.valueOf(sY.getText())));
		circle.setRadius(Integer.valueOf(radius.getText()));
		circle.setColor(
				new Color(Integer.valueOf(r.getText()), Integer.valueOf(g.getText()), Integer.valueOf(b.getText())));
	}
}

