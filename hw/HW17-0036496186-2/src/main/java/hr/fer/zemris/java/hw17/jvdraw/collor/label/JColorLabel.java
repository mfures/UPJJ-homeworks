package hr.fer.zemris.java.hw17.jvdraw.collor.label;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.collor.util.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.collor.util.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.collor.util.JColorArea;

/**
 * Simple label that describes {@link JColorArea}
 * 
 * @author mfures
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {
	private static final long serialVersionUID = 5427149425466463355L;

	/**
	 * Foreground color area
	 */
	private JColorArea fgColorArea;

	/**
	 * Background color area
	 */
	private JColorArea bgColorArea;

	/**
	 * Constructor
	 * 
	 * @param fgColorArea area
	 * @param bgColorArea area
	 */
	public JColorLabel(JColorArea fgColorArea, JColorArea bgColorArea) {
		this.fgColorArea = fgColorArea;
		fgColorArea.addColorChangeListener(this);
		this.bgColorArea = bgColorArea;
		bgColorArea.addColorChangeListener(this);

		updateText();
	}

	/**
	 * Updates text
	 */
	private void updateText() {
		StringBuilder sb = new StringBuilder();
		buildText(sb);
		setText(sb.toString());
	}

	/**
	 * Creates text
	 * 
	 * @param sb string builder
	 */
	private void buildText(StringBuilder sb) {
		sb.append("Foreground color: (");
		Color fgCol = fgColorArea.getCurrentColor();
		sb.append(fgCol.getRed());
		sb.append(", ");
		sb.append(fgCol.getGreen());
		sb.append(", ");
		sb.append(fgCol.getBlue());
		sb.append("), background color: ");
		Color bgCol = bgColorArea.getCurrentColor();
		sb.append(bgCol.getRed());
		sb.append(", ");
		sb.append(bgCol.getGreen());
		sb.append(", ");
		sb.append(bgCol.getBlue());
		sb.append(").");
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}
}
