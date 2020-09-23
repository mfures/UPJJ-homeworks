package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

public class DropdCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (!arguments.trim().isEmpty()) {
			env.writeln("Popd command doesn't take arguments");
			return ShellStatus.CONTINUE;
		}

		Object el = env.getSharedData(Utility.SHARED_DATA_KEY);
		if (el == null) {
			env.writeln("No pushed directory found.");
			return ShellStatus.CONTINUE;
		} else if (((Stack<Path>) el).isEmpty()) {
			env.writeln("No pushed directory found.");
			return ShellStatus.CONTINUE;
		}

		((Stack<Path>) el).pop();

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command dropd can be run with only zero arguments");
		list.add("Command tries to drop last pushed active direcotry");
		return list;
	}

}
