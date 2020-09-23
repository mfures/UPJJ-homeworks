package hr.fer.zemris.java.gui.layouts;

/**
 * Class that defines position of element in some grid. Has 2 read only
 * attributes. Row and column.
 * 
 * @author matfures
 *
 */
public class RCPosition {
	/**
	 * Used for defining position of element in some grid.
	 */
	private int row, column;

	/**
	 * Creates said position with given atributes
	 * 
	 * @param row    value
	 * @param colomn value
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row
	 * 
	 * @return value of row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column
	 * 
	 * @return value of column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
