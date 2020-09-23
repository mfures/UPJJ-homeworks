package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Line;

public class LineEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = -3863102941525871482L;

	/**
	 * Line
	 */
	private Line line;

	/**
	 * Start x
	 */
	private JTextField sX;
	/**
	 * End x
	 */
	private JTextField eX;

	/**
	 * Start y
	 */
	private JTextField sY;

	/**
	 * End y
	 */
	private JTextField eY;

	/**
	 * Color components
	 */
	private JTextField r, g, b;

	/**
	 * Constructor
	 * 
	 * @param line to edit
	 */
	public LineEditor(Line line) {
		this.line = line;
		sX = new JTextField(String.valueOf(line.getStart().x), 4);
		eX = new JTextField(String.valueOf(line.getEnd().x), 4);
		sY = new JTextField(String.valueOf(line.getStart().y), 4);
		eY = new JTextField(String.valueOf(line.getEnd().y), 4);
		r = new JTextField(String.valueOf(line.getColor().getRed()), 4);
		g = new JTextField(String.valueOf(line.getColor().getGreen()), 4);
		b = new JTextField(String.valueOf(line.getColor().getBlue()), 4);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel start = new JPanel();
		start.add(new JLabel("Start x:"));
		start.add(sX);
		start.add(new JLabel("Start y:"));
		start.add(sY);
		add(start);

		JPanel end = new JPanel();
		end.add(new JLabel("End x:"));
		end.add(eX);
		end.add(new JLabel("End y:"));
		end.add(eY);
		add(end);

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
		int rc, gc, bc;
		try {
			Integer.valueOf(sX.getText());
			Integer.valueOf(sY.getText());
			Integer.valueOf(eX.getText());
			Integer.valueOf(eY.getText());
			rc = Integer.valueOf(r.getText());
			gc = Integer.valueOf(g.getText());
			bc = Integer.valueOf(b.getText());
		} catch (Exception e) {
			throw new RuntimeException("Invalid values");
		}
		if (rc < 0 || rc > 255 || gc < 0 || gc > 255 || bc < 0 || bc > 255) {
			throw new RuntimeException("Invalid color values");
		}
	}

	@Override
	public void acceptEditing() {
		line.setStart(new Point(Integer.valueOf(sX.getText()), Integer.valueOf(sY.getText())));
		line.setEnd(new Point(Integer.valueOf(eX.getText()), Integer.valueOf(eY.getText())));
		line.setColor(
				new Color(Integer.valueOf(r.getText()), Integer.valueOf(g.getText()), Integer.valueOf(b.getText())));
	}
}
