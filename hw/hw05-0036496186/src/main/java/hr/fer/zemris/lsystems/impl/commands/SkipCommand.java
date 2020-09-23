package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Simple class that implements Command interface and skips current command
 * 
 * @author Matej Fureš
 *
 */
public class SkipCommand implements Command {
	/**
	 * Step for drawing, distance from current positon
	 */
	private double step;

	/**
	 * Constructor for SkipCommand
	 * 
	 * @param step to be initialized
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Executes command which shifts turtle on top of stack and updates its position
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D currentPosition = ctx.getCurrentState().getPosition();
		Vector2D orientation = ctx.getCurrentState().getOrientation();

		currentPosition.translate(orientation.scaled(step * ctx.getCurrentState().getMovesDistanceScale()));
	}
}
