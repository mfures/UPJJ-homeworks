package hr.fer.zemris.java.hw17.jvdraw.collor.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Color area
 * 
 * @author mfures
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	private static final long serialVersionUID = -2786473947261719868L;

	/**
	 * Current color
	 */
	private Color color;

	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param color to be set
	 */
	public JColorArea(Color color) {
		this.color = color;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Select new color", color);
				if (newColor != null) {
					Color prev = color;
					JColorArea.this.color = newColor;
					colorChanged(prev);
					repaint();
				}
			}
		});
	}

	/**
	 * Called when color changes
	 * 
	 * @param prev previous color
	 */
	private void colorChanged(Color prev) {
		listeners.forEach(x -> x.newColorSelected(this, prev, color));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		Insets insets = getInsets();
		Dimension dimension = getSize();
		g2d.fillRect(insets.left, insets.top, dimension.width, dimension.height);
	}

	@Override
	public Color getCurrentColor() {
		return color;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
