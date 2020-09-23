package hr.fer.zemris.java.hw17.jvdraw.drawing.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;

/**
 * Simple list model
 * 
 * @author mfures
 *
 */
public class DrawingObjectsListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	private static final long serialVersionUID = 8749466076255833261L;

	/**
	 * Drawing model used for drawing
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor
	 * 
	 * @param drawingModel to track
	 */
	public DrawingObjectsListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
