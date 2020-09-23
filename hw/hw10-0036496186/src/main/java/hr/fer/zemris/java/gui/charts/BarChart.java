package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Defines a BarChart object. It is defined by its x values and corresponding y
 * values and axis names
 * 
 * @author matfures
 *
 */
public class BarChart {
	/**
	 * List containing values on graph
	 */
	private List<XYValue> list;

	/**
	 * Names for axis
	 */
	private String xAxisName, yAxisName;

	/**
	 * Minimum y value on graph
	 */
	private int yMin;

	/**
	 * Maximum y value on chart
	 */
	private int yMax;

	/**
	 * Step on grid
	 */
	private int yDif;

	/**
	 * Constructors. Null values aren't permitted. If values aren't valid, an
	 * exception will be thrown
	 * 
	 * @param list      of values
	 * @param xAxisName name of axis x
	 * @param yAxisName name of axis y
	 * @param yMin      value of minimal y
	 * @param yMax      value of maximal y
	 * @param yDif      step between
	 * 
	 * @throws NullPointerException     if anything is null
	 * @throws IllegalArgumentException if arguments aren't valid
	 */
	public BarChart(List<XYValue> list, String xAxisName, String yAxisName, int yMin, int yMax, int yDif) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(xAxisName);
		Objects.requireNonNull(yAxisName);

		if (yMin < 0) {
			throw new IllegalArgumentException("Y min must be non negative");
		}

		if (yMin >= yMax) {
			throw new IllegalArgumentException("Y min must smaller that Y max");
		}

		if (yDif > (yMax - yMin)) {
			throw new IllegalArgumentException("Y step mustn't be greater that Y max - Y min");
		}

		int distance = yMax - yMin;
		while (distance % yDif != 0) {
			yDif++;
		}

		for (XYValue val : list) {
			Objects.requireNonNull(val);

			if (val.getY() < yMin) {
				throw new IllegalArgumentException("All Y values should be smaller than Y min");
			}
		}
		this.list = list;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDif = yDif;
	}

	/**
	 * Returns list of values
	 * 
	 * @return list of values
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns xAxisName
	 * 
	 * @return xAxisName
	 */
	public String getxAxisName() {
		return xAxisName;
	}

	/**
	 * Returns yAxisName
	 * 
	 * @return yAxisName
	 */
	public String getyAxisName() {
		return yAxisName;
	}

	/**
	 * Returns yMin
	 * 
	 * @return yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns yMax
	 * 
	 * @return yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns yDif
	 * 
	 * @return yDif
	 */
	public int getyDif() {
		return yDif;
	}
}
