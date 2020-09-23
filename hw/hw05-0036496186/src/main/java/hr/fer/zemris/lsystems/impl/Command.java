package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Defines a functional Interface with method execute
 * 
 * @author Matej Fureš
 *
 */
public interface Command {
	/**
	 * Defines a command that does something with TurtleState on top of context
	 * 
	 * @param ctx     on which is operated
	 * @param painter painter whose draw method is used
	 */
	void execute(Context ctx, Painter painter);
}
