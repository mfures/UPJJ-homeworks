package hr.fer.zemris.java.gui.charts;

/**
 * A class containing 2 read only variables;x and y
 * 
 * @author matfures
 *
 */
public class XYValue {
	/**
	 * x and y values. Read only
	 */
	private int x, y;

	/**
	 * Constructor
	 * 
	 * @param x value
	 * @param y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x value
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y value
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
