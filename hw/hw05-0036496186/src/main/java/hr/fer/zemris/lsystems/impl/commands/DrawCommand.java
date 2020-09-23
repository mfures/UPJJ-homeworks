package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Simple class that implements Command interface and draws a line
 * 
 * @author Matej Fureš
 *
 */
public class DrawCommand implements Command {
	/**
	 * Step for drawing, distance from current positon
	 */
	private double step;

	/**
	 * Constructor for DrawCommand
	 * 
	 * @param step to be initialized
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Executes draw command which shifts turtle on top of stack and updates its
	 * position
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();
		Vector2D startingPosition = currentPosition.copy();
		Vector2D orientation = currentState.getOrientation();

		currentPosition.translate(orientation.scaled(step * currentState.getMovesDistanceScale()));

		painter.drawLine(startingPosition.getX(), startingPosition.getY(), currentPosition.getX(),
				currentPosition.getY(), currentState.getDrawingColor(), 1f);
	}

}
