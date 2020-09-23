package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Simple class that implements Command interface and does pop command.
 * 
 * @author Matej Fureš
 *
 */
public class PopCommand implements Command {
	/**
	 * Executes pop command which removes top state of context
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
