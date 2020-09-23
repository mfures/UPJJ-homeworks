package hr.fer.zemris.java.hw17.jvdraw.drawing.model;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectListener;

/**
 * Drawing model used for holding {@link GeometricalObject}s
 * 
 * @author mfures
 *
 */
public interface DrawingModel extends GeometricalObjectListener {
	/**
	 * Number of stored {@link GeometricalObject}s
	 * 
	 * @return number of objects
	 */
	public int getSize();

	/**
	 * Getter for object on given index
	 * 
	 * @param index of object
	 * @return object on said index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds object to collection
	 * 
	 * @param object to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes object from collection
	 * 
	 * @param object to remove
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes order in collection
	 * 
	 * @param object object
	 * @param offset to add
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Index of given object or -1
	 * 
	 * @param object to look for
	 * @return position
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * CLears the collection
	 */
	public void clear();

	/**
	 * Resets modified flag to false
	 */
	public void clearModifiedFlag();

	/**
	 * Getter for modified flag
	 * 
	 * @return modified flag
	 */
	public boolean isModified();

	/**
	 * Adds listener to model
	 * 
	 * @param l to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes listener from model
	 * 
	 * @param l to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
