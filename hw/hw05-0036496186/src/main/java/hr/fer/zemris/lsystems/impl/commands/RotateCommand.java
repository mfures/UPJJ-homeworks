package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Simple class that implements Command interface and pushes a copy of top state
 * on stack to stack.
 * 
 * @author Matej Fureš
 *
 */
public class RotateCommand implements Command {
	/**
	 * Angle for which the turtle is rotated
	 */
	private double angleInRadian;

	/**
	 * Constructor that sets its angle value to given angle, and rotates state on
	 * top of stack for given value
	 * 
	 * @param angleInDegree angle used for rotation
	 */
	public RotateCommand(double angleInDegree) {
		angleInRadian = angleInDegree * Math.PI / 180;
	}

	/**
	 * Rotates Turtle on top of stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getOrientation().rotate(angleInRadian);
	}

}
