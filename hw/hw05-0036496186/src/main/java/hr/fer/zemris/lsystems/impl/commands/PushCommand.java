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
public class PushCommand implements Command {
	/**
	 * Pushes top of stack to stack as a copy
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
