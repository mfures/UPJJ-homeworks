package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Used for coloring
 * 
 * @author Matej Fureš
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>

{
	/**
	 * Reference pixel
	 */
	private Pixel reference;

	/**
	 * Picture that is colored
	 */
	private Picture picture;

	/**
	 * Color used for filing
	 */
	private int fillColor;

	/**
	 * Color of referenced pixel
	 */
	private int refColor;

	/**
	 * Constructor. Arguments mustn't be null
	 * 
	 * @param reference pixel
	 * @param picture   to be colored
	 * @param fillColor used for filling
	 * @throws NullPointerException     if arguments are null
	 * @throws IllegalArgumentException if arguments aren't in format
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		Objects.requireNonNull(reference);
		Objects.requireNonNull(picture);

		this.picture = picture;
		if (!inBounds(reference.getX(), reference.getY())) {
			throw new IllegalArgumentException("Pixel out of bounds");
		}

		if ((fillColor & 0xFF000000) != 0) {
			throw new IllegalArgumentException("Color not in format");
		}

		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel t) {
		return refColor == picture.getPixelColor(t.getX(), t.getY());
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> list = new LinkedList<Pixel>();
		int x = t.getX(), y = t.getY();

		if (inBounds(x + 1, t.getY())) {
			list.add(new Pixel(x + 1, y));
		}
		if (inBounds(x - 1, y)) {
			list.add(new Pixel(x - 1, y));

		}
		if (inBounds(x, y + 1)) {
			list.add(new Pixel(x, y + 1));

		}
		if (inBounds(x, y - 1)) {
			list.add(new Pixel(x, y - 1));
		}

		return list;
	}

	/**
	 * Checks if picture contains given coordinates
	 * 
	 * @param x to be checked
	 * @param y to be checked
	 * @return true if contained
	 */
	private boolean inBounds(int x, int y) {
		if (y < 0 || x < 0) {
			return false;
		}

		return (picture.getHeight() > y) && (picture.getWidth() > x);
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.getX(), t.getY(), fillColor);
	}
}
