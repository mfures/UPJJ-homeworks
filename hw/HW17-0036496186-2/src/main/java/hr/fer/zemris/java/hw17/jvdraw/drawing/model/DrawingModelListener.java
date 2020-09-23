package hr.fer.zemris.java.hw17.jvdraw.drawing.model;

/**
 * SImple listener for {@link DrawingModel}
 * 
 * @author mfures
 *
 */
public interface DrawingModelListener {
	/**
	 * Triggers when an object is added
	 * 
	 * @param source source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Triggers when an object is removed
	 * 
	 * @param source source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Triggers when an object is changed
	 * 
	 * @param source source
	 * @param index0 start index
	 * @param index1 end index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
