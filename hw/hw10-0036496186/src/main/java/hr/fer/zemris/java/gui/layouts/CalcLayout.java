package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom layout manager 2 implementation which puts component into a grid of
 * 5*7 table
 * 
 * @author matfures
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Default number of rows in layout
	 */
	private static final int NUM_OF_ROWS = 5;

	/**
	 * Default number of columns in layout
	 */
	private static final int NUM_OF_COLUMNS = 7;

	/**
	 * Default gap between components
	 */
	private static final int DEFAULT_GAP = 0;

	/**
	 * Map that stores components
	 */
	private Map<RCPosition, Entry> map;

	/**
	 * Gap between components in rows and columns
	 */
	private int gap;

	/**
	 * Creates CalcLayout with predefined gap between components
	 * 
	 * @param gap to be used
	 */
	public CalcLayout(int gap) {
		this.gap = gap;

		map = new HashMap<>();
	}

	/**
	 * Creates CalcLayout with predefined gap between components set to 0
	 */
	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	/**
	 * Not used.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This add method shouldn't be used");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (var entry : map.entrySet()) {
			if (entry.getValue().getComponent() == comp) {
				map.remove(entry.getKey());
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if (map.isEmpty()) {
			return new Dimension(0, 0);
		}

		double width = map.values().stream().map(x -> x.getPrefSizeWidth()).max(Double::compare).get();
		double height = map.values().stream().map(x -> x.getPrefSizeHeight()).max(Double::compare).get();

		return new Dimension((int) Math.ceil(NUM_OF_COLUMNS * (width + gap) - gap),
				(int) Math.ceil(NUM_OF_ROWS * (height + gap) - gap));
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		if (map.isEmpty()) {
			return new Dimension(0, 0);
		}

		double width = map.values().stream().map(x -> x.getMinSizeWidth()).max(Double::compare).get();
		double height = map.values().stream().map(x -> x.getMinSizeHeight()).max(Double::compare).get();

		return new Dimension((int) Math.ceil(NUM_OF_COLUMNS * (width + gap) - gap),
				(int) Math.ceil(NUM_OF_ROWS * (height + gap) - gap));
	}

	@Override
	public void layoutContainer(Container parent) {
		int width = (parent.getWidth() - (NUM_OF_COLUMNS - 1) * gap) / NUM_OF_COLUMNS;
		int height = (parent.getHeight() - (NUM_OF_ROWS - 1) * gap) / NUM_OF_ROWS;

		int extraWidth = parent.getWidth() - (width * 7 + gap * 6);
		int extraHeight = parent.getHeight() - (height * 5 + gap * 4);

		RCPosition position;

		int[][] bonusDistance = bonusDistance(extraWidth, extraHeight);

		for (var entry : map.entrySet()) {
			position = entry.getKey();

			if (position.getColumn() == 1 && position.getRow() == 1) {
				entry.getValue().component.setBounds(0, 0,
						(width + gap) * entry.getValue().numOfSlots - gap - bonusDistance[0][position.getColumn() - 1]
								+ bonusDistance[0][position.getColumn() + entry.getValue().numOfSlots - 1],
						height + bonusDistance[1][position.getRow()] - bonusDistance[1][position.getRow() - 1]);
			} else {
				entry.getValue().component.setBounds(
						(position.getColumn() - 1) * (width + gap) + bonusDistance[0][position.getColumn() - 1],
						(position.getRow() - 1) * (height + gap) + bonusDistance[1][position.getRow() - 1],
						width + bonusDistance[0][position.getColumn()] - bonusDistance[0][position.getColumn() - 1],
						height + bonusDistance[1][position.getRow()] - bonusDistance[1][position.getRow() - 1]);
			}
		}
	}

	/**
	 * Calculates bonus distance for width and height. Bonus offset of some position
	 * is simply accessed in array on its index, and if the component itself is
	 * longer its accessed by subing index of previous position
	 * 
	 * @param extraWidth  for current container
	 * @param extraHeight for current container
	 * @return array of bonus distances
	 */
	private int[][] bonusDistance(int extraWidth, int extraHeight) {
		int[][] bonusDistance = new int[2][8];

		if (extraWidth == 0) {
			bonusDistance[0] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		} else if (extraWidth == 1) {
			bonusDistance[0] = new int[] { 0, 0, 0, 0, 1, 1, 1, 1 };
		} else if (extraWidth == 2) {
			bonusDistance[0] = new int[] { 0, 1, 1, 1, 1, 1, 1, 2 };
		} else if (extraWidth == 3) {
			bonusDistance[0] = new int[] { 0, 1, 1, 1, 2, 2, 2, 3 };
		} else if (extraWidth == 4) {
			bonusDistance[0] = new int[] { 0, 1, 1, 2, 2, 3, 3, 4 };
		} else if (extraWidth == 5) {
			bonusDistance[0] = new int[] { 0, 1, 1, 2, 3, 4, 4, 5 };
		} else if (extraWidth == 6) {
			bonusDistance[0] = new int[] { 0, 1, 2, 3, 3, 4, 5, 6 };
		}

		if (extraHeight == 0) {
			bonusDistance[1] = new int[] { 0, 0, 0, 0, 0, 0 };
		} else if (extraHeight == 1) {
			bonusDistance[1] = new int[] { 0, 0, 0, 1, 1, 1 };
		} else if (extraHeight == 2) {
			bonusDistance[1] = new int[] { 0, 1, 1, 1, 1, 2 };
		} else if (extraHeight == 3) {
			bonusDistance[1] = new int[] { 0, 1, 1, 2, 2, 3 };
		} else if (extraHeight == 4) {
			bonusDistance[1] = new int[] { 0, 1, 2, 2, 3, 4 };
		}

		return bonusDistance;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof String) {
			constraints = parseConstraint((String) constraints);
		} else if (!(constraints instanceof RCPosition)) {
			throw new CalcLayoutException("Invalid type sent as constraint. Supported types are String and RCPosition");
		}

		RCPosition position = (RCPosition) constraints;

		if (position.getColumn() < 1 || position.getRow() < 1 || position.getRow() > NUM_OF_ROWS
				|| position.getColumn() > NUM_OF_COLUMNS) {
			throw new CalcLayoutException("Invalid RCPosition sent");
		}

		if (position.getRow() == 1) {
			if (position.getColumn() > 1 && position.getColumn() < 6) {
				throw new CalcLayoutException("Invalid RCPosition sent");
			}
		}

		for (var entry : map.entrySet()) {
			if (entry.getValue().getComponent() == comp) {
				throw new CalcLayoutException("Component already added");
			}
		}

		if (map.containsKey(position)) {
			throw new CalcLayoutException("Position already taken");
		}

		if (position.getRow() == 1 && position.getColumn() == 1) {
			map.put(position, new Entry(5, comp, gap));
		} else {
			map.put(position, new Entry(comp, gap));
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		if (map.isEmpty()) {
			return new Dimension(0, 0);
		}

		double width = map.values().stream().map(x -> x.getMaxSizeWidth()).max(Double::compare).get();
		double height = map.values().stream().map(x -> x.getMaxSizeHeight()).max(Double::compare).get();

		return new Dimension((int) Math.ceil(NUM_OF_COLUMNS * (width + gap) - gap),
				(int) Math.ceil(NUM_OF_ROWS * (height + gap) - gap));
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Parses given string into RC position if it is parseble. Otherwise throws an
	 * exception
	 * 
	 * @param input to be parsed
	 * @return RC position from input
	 * @throws CalcLayoutException if input is invalid
	 */
	private RCPosition parseConstraint(String input) {
		String[] stringNumbers = input.split(",");

		if (stringNumbers.length != 2) {
			throw new CalcLayoutException("Incorrect format of input string.");
		}

		int[] numbers = new int[2];

		for (int i = 0; i < 2; i++) {
			try {
				numbers[i] = Integer.parseInt(stringNumbers[i]);
			} catch (Exception e) {
				throw new CalcLayoutException("Incorrect format of numbers in string.");
			}
		}

		return new RCPosition(numbers[0], numbers[1]);
	}

	/**
	 * Defines an entry in the layout. Number of slots references number of
	 * consecutive table slots in a row component takes
	 * 
	 * @author matfures
	 *
	 */
	private static class Entry {
		/**
		 * Default number of slots
		 */
		private static final int DEFAULT_NUM_OF_SLOTS = 1;

		/**
		 * Number of slots component takes
		 */
		private int numOfSlots;

		/**
		 * Gap between components in rows and columns
		 */
		private int gap;

		/**
		 * Component added
		 */
		private Component component;

		/**
		 * Creates an entry, validity of slots isn't checked and should be checked
		 * before calling this constructor
		 * 
		 * @param numOfSlots component takes in a row
		 * @param component  that is added
		 * @param gap        between components
		 */
		private Entry(int numOfSlots, Component component, int gap) {
			this.numOfSlots = numOfSlots;
			this.component = component;
			this.gap = gap;

		}

		/**
		 * Creates an entry with default number of slots
		 * 
		 * @param component to be added
		 * @param gap       between components
		 */
		private Entry(Component component, int gap) {
			this(DEFAULT_NUM_OF_SLOTS, component, gap);
		}

		/**
		 * Getter for component
		 * 
		 * @return
		 */
		private Component getComponent() {
			return component;
		}

		/**
		 * Calculates minimum size of component and returns it
		 * 
		 * @return minimum size of width
		 */
		private double getMinSizeWidth() {
			if (numOfSlots == 1) {
				return component.getMinimumSize().width;
			}

			int size = (int) Math
					.ceil((component.getPreferredSize().width * 1.0 - gap * (numOfSlots - 1)) / numOfSlots);

			return size < 0 ? 0 : size;
		}

		/**
		 * Calculates minimum height size of component and returns it
		 * 
		 * @return minimum size of height
		 */
		private double getMinSizeHeight() {
			return component.getMinimumSize().height;
		}

		/**
		 * Calculates preferred size of component and returns it
		 * 
		 * @return preferred size of width
		 */
		private double getPrefSizeWidth() {
			if (numOfSlots == 1) {
				return component.getPreferredSize().width;
			}

			int size = (int) Math
					.ceil((component.getPreferredSize().width * 1.0 - gap * (numOfSlots - 1)) / numOfSlots);
			return size < 0 ? 0 : size;
		}

		/**
		 * Calculates preferred height size of component and returns it
		 * 
		 * @return preferred size of height
		 */
		private double getPrefSizeHeight() {
			return component.getPreferredSize().height;
		}

		/**
		 * Calculates maximum size of component and returns it
		 * 
		 * @return maximum size of width
		 */
		private double getMaxSizeWidth() {
			if (numOfSlots == 1) {
				return component.getMaximumSize().width;
			}

			int size = (int) Math
					.ceil((component.getPreferredSize().width * 1.0 - gap * (numOfSlots - 1)) / numOfSlots);

			return size < 0 ? 0 : size;
		}

		/**
		 * Calculates maximum height size of component and returns it
		 * 
		 * @return maximum size of height
		 */
		private double getMaxSizeHeight() {
			return component.getMaximumSize().height;
		}
	}
}
