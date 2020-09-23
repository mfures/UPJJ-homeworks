package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical;

import javax.swing.JPanel;

/**
 * Simple editor class that should be extended and used for editing
 * 
 * @author mfures
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 542282650281851974L;

	/**
	 * Checks if state of edit is valid
	 */
	public abstract void checkEditing(); 

	/**
	 * Used when changes have been made to apply them
	 */
	public abstract void acceptEditing();
}