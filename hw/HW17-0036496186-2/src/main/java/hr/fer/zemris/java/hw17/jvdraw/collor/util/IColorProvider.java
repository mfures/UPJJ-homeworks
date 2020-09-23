package hr.fer.zemris.java.hw17.jvdraw.collor.util;

import java.awt.Color;

/**
 * Provider of color
 * 
 * @author mfures
 *
 */
public interface IColorProvider {
	/**
	 * Getter for current color
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Registers listener to provider
	 * 
	 * @param l to be added
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes listener from provider
	 * 
	 * @param l to be removed
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
