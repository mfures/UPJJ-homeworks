package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Component that takes BarChart trough constructor and draws a BarChart that
 * describes it
 * 
 * @author matfures
 *
 */
public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;

	/**
	 * Gap on left side; in pixels
	 */
	private static final int GAP_LEFT = 8;

	/**
	 * Gap on bottom side; in pixels
	 */
	private static final int GAP_BOTTOM = 8;

	/**
	 * Gap for x values
	 */
	private static final int GAP_X = 20;

	/**
	 * Color of axis
	 */
	private static final Color AXIS_COLOR = Color.GRAY;

	/**
	 * Color of GRID
	 */
	private static final Color GRID_COLOR = Color.ORANGE;

	/**
	 * Color of background
	 */
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	/**
	 * Color of rectangles
	 */
	private static final Color RECTANGLE_COLOR = Color.RED;

	/**
	 * Arrows length
	 */
	private static final int ARROW_LENGTH = 7;

	/**
	 * Arrows tail
	 */
	private static final int ARROW_TAIL = 2;

	/**
	 * Font metrics used for numbers in columns
	 */
	private FontMetrics metricsNum;

	/**
	 * Font metrics used for labels
	 */
	private FontMetrics metricsLabel;

	/**
	 * Chart that defines this component
	 */
	private BarChart chart;

	/**
	 * Constructor. Chart mustn't be null
	 * 
	 * @param chart to be drawn
	 * @throws NullPointerException if chart is null
	 */
	public BarChartComponent(BarChart chart) {
		Objects.requireNonNull(chart);
		this.chart = chart;
	}

	@Override
	public void paintComponent(Graphics g) {
		Objects.requireNonNull(g);// ?
		metricsNum = numMetrics();
		metricsLabel = labelMetrics();

		int numOfRows = (chart.getyMax() - chart.getyMin()) / chart.getyDif();
		int numOfColumns = chart.getList().size();

		int widthOfRow = 0;
		for (int i = chart.getyMin(); i <= chart.getyMax(); i += chart.getyDif()) {
			widthOfRow = Math.max(widthOfRow, metricsNum.stringWidth(Integer.toString(i)));
		}

		int widthOfColumn = 0;
		List<XYValue> list = chart.getList();
		for (int i = 0; i < list.size(); i++) {
			widthOfColumn = Math.max(widthOfColumn, metricsNum.stringWidth(Integer.toString(list.get(i).getX())));
		}

		int labelSize = metricsLabel.getHeight();

		Insets insets = getInsets();
		Dimension dimension = getSize();

		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saveAT = g2.getTransform();

		g2.setColor(BACKGROUND_COLOR);
		g2.fillRect(insets.left, insets.top, dimension.width - insets.left - insets.right,
				dimension.height - insets.top - insets.bottom);

		int x = insets.left + GAP_LEFT;
		int y = dimension.height - insets.bottom - GAP_BOTTOM;
		int width = dimension.width - insets.left - insets.right - GAP_LEFT;
		int height = dimension.height - insets.top - insets.bottom - GAP_BOTTOM;
		drawXAxisLabel(x, y, width, height, g2);
		drawYAxisLabel(x, y, width, height, g2);
		g2.setTransform(saveAT);

		x += labelSize + (3 * GAP_LEFT) / 2 + widthOfRow;
		y -= (labelSize + GAP_BOTTOM + numMetrics().getAscent());
		width -= (labelSize + (3 * GAP_LEFT) / 2 + widthOfRow + ARROW_LENGTH + ARROW_TAIL);
		height -= (labelSize + GAP_BOTTOM + ARROW_LENGTH + ARROW_TAIL + numMetrics().getAscent());

		drawAxisGridAndArrows(x, y, width, height, numOfRows, numOfColumns, widthOfRow, widthOfColumn, chart.getyMin(),
				g2);
		g2.setTransform(saveAT);

	}

	/**
	 * Draws y label
	 * 
	 * @param x      starting x
	 * @param y      starting y
	 * @param width  of area
	 * @param height of area
	 * @param g2d    that draws
	 */
	private void drawYAxisLabel(int x, int y, int width, int height, Graphics2D g2d) {
		AffineTransform t = new AffineTransform();
		t.rotate(-Math.PI / 2);
		g2d.setTransform(t);
		g2d.setColor(Color.BLACK);
		g2d.drawString(chart.getyAxisName(), y, x);
		g2d.setFont(getFont().deriveFont(Font.PLAIN, 12));
		int sWidth = g2d.getFontMetrics().stringWidth(chart.getyAxisName());
		g2d.drawString(chart.getyAxisName(), -(y - (height - sWidth) / 2), x + metricsLabel.getAscent());
	}

	/**
	 * Draws x label
	 * 
	 * @param x      starting x
	 * @param y      starting y
	 * @param width  of area
	 * @param height of area
	 * @param g2d    that draws
	 */
	private void drawXAxisLabel(int x, int y, int width, int height, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(getFont().deriveFont(Font.PLAIN, 12));
		int sWidth = g2d.getFontMetrics().stringWidth(chart.getxAxisName());
		g2d.drawString(chart.getxAxisName(), x + (width - sWidth) / 2, y);
	}

	/**
	 * Draws grid section of component;Axis,grid,rectangles,numbers and arrows.
	 * 
	 * @param x             starting x
	 * @param y             starting y
	 * @param width         of area
	 * @param height        of area
	 * @param numOfRows     to draw
	 * @param numOfCols     to draw
	 * @param widthOfRow    rows width
	 * @param widthOfColumn columns width
	 * @param min           minimal value on y axis
	 * @param g2d           that draws
	 */
	private void drawAxisGridAndArrows(int x, int y, int width, int height, int numOfRows, int numOfCols,
			int widthOfRow, int widthOfColumn, int min, Graphics2D g2d) {
		List<XYValue> list = chart.getList();
		// axis
		g2d.setColor(Color.BLACK);
		g2d.setFont(getFont().deriveFont(Font.BOLD, 14));
		int sWidth = g2d.getFontMetrics().stringWidth(Integer.toString(min));
		g2d.drawString(Integer.toString(min), x - GAP_LEFT / 2 - sWidth, y + numMetrics().getAscent() / 2);

		g2d.setColor(AXIS_COLOR);
		g2d.drawLine(x, y + GAP_BOTTOM / 2, x, y - height - ARROW_TAIL);
		g2d.drawLine(x - GAP_LEFT / 2, y, x + width + ARROW_TAIL, y);

		// grid
		int incremenentY = height / numOfRows;
		int extraPixels = width % numOfCols;
		for (int i = 1; i <= numOfRows; i++) {
			g2d.setColor(Color.BLACK);
			g2d.setFont(getFont().deriveFont(Font.BOLD, 14));
			sWidth = g2d.getFontMetrics().stringWidth(Integer.toString(min + i * chart.getyDif()));
			g2d.drawString(Integer.toString(min + i * chart.getyDif()), x - GAP_LEFT / 2 - sWidth,
					y - incremenentY * i + numMetrics().getAscent() / 2);

			g2d.setColor(AXIS_COLOR);
			g2d.drawLine(x - GAP_LEFT / 2, y - incremenentY * i, x + 1, y - incremenentY * i);
			g2d.setColor(GRID_COLOR);
			g2d.drawLine(x + 1, y - incremenentY * i, x + width - extraPixels, y - incremenentY * i);
		}

		int incremenentX = width / numOfCols;
		extraPixels = height % numOfRows;
		for (int i = 1; i <= numOfCols; i++) {
			g2d.setColor(Color.BLACK);
			g2d.setFont(getFont().deriveFont(Font.BOLD, 14));
			sWidth = g2d.getFontMetrics().stringWidth(Integer.toString(list.get(i - 1).getX()));
			g2d.drawString(Integer.toString(list.get(i - 1).getX()), x + i * incremenentX - (incremenentX + sWidth) / 2,
					y + GAP_BOTTOM / 2 + numMetrics().getAscent());

			g2d.setColor(AXIS_COLOR);
			g2d.drawLine(x + i * incremenentX, y + GAP_BOTTOM / 2, x + i * incremenentX, y - 1);
			g2d.setColor(GRID_COLOR);
			g2d.drawLine(x + i * incremenentX, y - 1, x + i * incremenentX, y - height + extraPixels);
		}

		// arrows
		g2d.setColor(AXIS_COLOR);
		g2d.fillPolygon(
				new int[] { x + width + ARROW_TAIL, x + width + ARROW_TAIL, x + width + ARROW_TAIL + ARROW_LENGTH },
				new int[] { y + ARROW_LENGTH, y - ARROW_LENGTH, y }, 3);
		g2d.fillPolygon(new int[] { x + ARROW_LENGTH, x - ARROW_LENGTH, x },
				new int[] { y - height - ARROW_TAIL, y - height - ARROW_TAIL, y - height - ARROW_TAIL - ARROW_LENGTH },
				3);

		// rectangles
		XYValue current;
		g2d.setColor(RECTANGLE_COLOR);
		for (int i = 0; i < numOfCols; i++) {
			current = list.get(i);
			g2d.fillRect(x + incremenentX * i + 1,
					(int) (y - incremenentY * ((current.getY() - chart.getyMin()) * 1.0 / chart.getyDif())),
					incremenentX - 1,
					(int) (incremenentY * ((current.getY() - chart.getyMin()) * 1.0 / chart.getyDif())));
		}
	}

	/**
	 * Font metrics for numbers
	 * 
	 * @return number font metrics
	 */
	private FontMetrics numMetrics() {
		return getFontMetrics(getFont().deriveFont(Font.BOLD, 14));
	}

	/**
	 * Font metrics for labels
	 * 
	 * @return label font metrics
	 */
	private FontMetrics labelMetrics() {
		return getFontMetrics(getFont().deriveFont(Font.PLAIN, 12));
	}

	@Override
	public Dimension getPreferredSize() {
		metricsNum = numMetrics();
		metricsLabel = labelMetrics();

		int widthOfRow = 0;
		for (int i = chart.getyMin(); i <= chart.getyMax(); i += chart.getyDif()) {
			widthOfRow = Math.max(widthOfRow, metricsNum.stringWidth(Integer.toString(i)));
		}

		int widthOfColumn = 0;
		List<XYValue> list = chart.getList();
		for (int i = 0; i < list.size(); i++) {
			widthOfColumn = Math.max(widthOfColumn, metricsNum.stringWidth(Integer.toString(list.get(i).getX())));
		}

		int width = GAP_LEFT * 5 / 2 + metricsLabel.getHeight() + widthOfRow
				+ chart.getList().size() * (2 * GAP_X + widthOfColumn);

		int height = GAP_BOTTOM * 5 / 2 + metricsLabel.getHeight() + metricsNum.getHeight()
				+ metricsNum.getHeight() * ((chart.getyMax() - chart.getyMin()) / chart.getyDif());

		return new Dimension(Math.max(width, 2 * metricsLabel.stringWidth(chart.getxAxisName())),
				Math.max(height, 2 * metricsLabel.stringWidth(chart.getyAxisName())));
	}
}
