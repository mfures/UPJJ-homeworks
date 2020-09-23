package hr.fer.zemris.java.hw17.jvdraw.drawing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;

/**
 * Implementation of drawing model
 * 
 * @author mfures
 *
 */
public class DrawingModelImpl implements DrawingModel {
	/**
	 * List of geometrical objects
	 */
	private List<GeometricalObject> objects = new ArrayList<>();

	/**
	 * Stores registered listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	/**
	 * Modified flag
	 */
	private boolean modified;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);
		listeners.forEach(l -> l.objectsAdded(this, objects.size(), objects.size()));
		modified = true;

	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		listeners.forEach(l -> l.objectsRemoved(this, index, index));
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if (index + offset < 0)
			return;
		if (index + offset > objects.size() - 1)
			return;

		Collections.swap(objects, index, index + offset);
		listeners.forEach(l -> l.objectsChanged(this, index, index + offset));
		modified = true;
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		for (int i = objects.size() - 1; i >= 0; i--) {
			remove(objects.get(i));
		}
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		listeners.forEach(l -> l.objectsChanged(this, objects.indexOf(o), objects.indexOf(o)));
	}
}
