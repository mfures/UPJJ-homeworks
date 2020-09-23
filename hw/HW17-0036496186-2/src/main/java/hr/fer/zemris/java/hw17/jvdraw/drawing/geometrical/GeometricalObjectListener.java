package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical;

/**
 * Listener for {@link GeometricalObject}
 * 
 * @author mfures
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Triggers when {@link GeometricalObject} has changed
	 * 
	 * @param o that changed
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}