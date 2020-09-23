package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Simple class that implements Command interface and changes color of drawing
 * 
 * @author Matej Fureš
 *
 */
public class ColorCommand implements Command {
	/**
	 * Step for drawing, distance from current positon
	 */
	private Color color;

	/**
	 * Constructor for ColorCommand
	 * 
	 * @param color to be initialized
	 * @throws NullPointerException if color is null
	 */
	public ColorCommand(Color color) {
		Objects.requireNonNull(color);

		this.color = color;
	}

	/**
	 * Executes command which shifts turtles color to given color
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setDrawingColor(color);
	}
}
