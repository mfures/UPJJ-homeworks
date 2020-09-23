package coloring.algorithms;

/**
 * Defines pixel as used in rest of assignment
 * 
 * @author Matej Fureš
 *
 */
public class Pixel {
	/**
	 * X coordinate
	 */
	private int x;

	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Constructor
	 * 
	 * @param x to be set
	 * @param y to be set
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
