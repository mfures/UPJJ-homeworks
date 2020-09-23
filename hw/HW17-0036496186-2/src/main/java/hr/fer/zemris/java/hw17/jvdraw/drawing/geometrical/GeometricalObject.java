package hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a {@link GeometricalObject} as used in implementation
 * 
 * @author mfures
 *
 */
public abstract class GeometricalObject {
	/**
	 * Holds listeners that have been registered to this {@link GeometricalObject}
	 */
	protected List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Used to accept {@link GeometricalObjectVisitor}
	 * 
	 * @param v to accept
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Used to create {@link GeometricalObjectEditor}
	 * 
	 * @return new {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Registers {@link GeometricalObjectListener} to this {@link GeometricalObject}
	 * 
	 * @param l to register
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Removes said {@link GeometricalObjectListener} from this
	 * {@link GeometricalObject}
	 * 
	 * @param l to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
}
