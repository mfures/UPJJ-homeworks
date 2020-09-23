package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Simple class that implements Command interface and changes its scale
 * 
 * @author Matej Fureš
 *
 */
public class ScaleCommand implements Command {
	/**
	 * Step for drawing, distance from current positon
	 */
	private double factor;

	/**
	 * Constructor for ScaleCommand
	 * 
	 * @param color to be initialized
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Executes command which shifts turtles scaler to given factor
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double moveLength = ctx.getCurrentState().getMovesDistanceScale();
		
		ctx.getCurrentState().setMovesDistanceScale(moveLength * factor);
	}
}
