package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;

public class FilledCircleEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = -3863102941525871482L;

	/**
	 * Filled circle
	 */
	private FilledCircle circle;
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
	 * Background color components
	 */
	private JTextField r2, g2, b2;

	/**
	 * Constructor
	 * 
	 * @param circle to edit
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;
		sX = new JTextField(String.valueOf(circle.getStart().x), 4);
		sY = new JTextField(String.valueOf(circle.getStart().y), 4);
		radius = new JTextField(String.valueOf(circle.getRadius()), 4);

		r = new JTextField(String.valueOf(circle.getColor().getRed()), 4);
		g = new JTextField(String.valueOf(circle.getColor().getGreen()), 4);
		b = new JTextField(String.valueOf(circle.getColor().getBlue()), 4);

		r2 = new JTextField(String.valueOf(circle.getBgColor().getRed()), 4);
		g2 = new JTextField(String.valueOf(circle.getBgColor().getGreen()), 4);
		b2 = new JTextField(String.valueOf(circle.getBgColor().getBlue()), 4);

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
		color.add(new JLabel("FOREGROUND RED:"));
		color.add(r);
		color.add(new JLabel("FOREGROUND GREEN:"));
		color.add(g);
		color.add(new JLabel("FOREGROUND BLUE:"));
		color.add(b);
		add(color);

		JPanel bcolor = new JPanel();
		bcolor.add(new JLabel("BACKGROUND RED:"));
		bcolor.add(r2);
		bcolor.add(new JLabel("BACKGROUND GREEN:"));
		bcolor.add(g2);
		bcolor.add(new JLabel("BACKGROUND BLUE:"));
		bcolor.add(b2);
		add(bcolor);
	}

	@Override
	public void checkEditing() {
		int rc, gc, bc, rc2, gc2, bc2, rad;
		try {
			Integer.valueOf(sX.getText());
			Integer.valueOf(sY.getText());
			rad = Integer.valueOf(radius.getText());
			rc = Integer.valueOf(r.getText());
			gc = Integer.valueOf(g.getText());
			bc = Integer.valueOf(b.getText());
			rc2 = Integer.valueOf(r2.getText());
			gc2 = Integer.valueOf(g2.getText());
			bc2 = Integer.valueOf(b2.getText());
		} catch (Exception e) {
			throw new RuntimeException("Invalid values");
		}
		if (rc < 0 || rc > 255 || gc < 0 || gc > 255 || bc < 0 || bc > 255) {
			throw new RuntimeException("Invalid color values");
		}
		if (rc2 < 0 || rc2 > 255 || gc2 < 0 || gc2 > 255 || bc2 < 0 || bc2 > 255) {
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
		circle.setBgColor(
				new Color(Integer.valueOf(r2.getText()), Integer.valueOf(g2.getText()), Integer.valueOf(b2.getText())));
	}
}
