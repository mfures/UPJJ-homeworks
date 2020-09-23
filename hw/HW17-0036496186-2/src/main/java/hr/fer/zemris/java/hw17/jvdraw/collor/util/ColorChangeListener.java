package hr.fer.zemris.java.hw17.jvdraw.collor.util;

import java.awt.Color;

/**
 * Listener for color change
 * 
 * @author mfures
 *
 */
public interface ColorChangeListener {
	/**
	 * Triggers when new color has been selected
	 * 
	 * @param source   source
	 * @param oldColor old
	 * @param newColor new
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
