package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Help command that provides help utility for shell
 * 
 * @author Matej Fureš
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("Commands used in shell are:");
			env.commands().forEach((k, v) -> env.writeln(k));
			return ShellStatus.CONTINUE;
		}

		String args[] = arguments.split("\\s+");

		if (args.length == 1) {
			ShellCommand command = env.commands().get(args[0]);

			if (command == null) {
				env.writeln("Given command doesn't exist.");
			} else {
				command.getCommandDescription().forEach((x) -> env.writeln(x));
			}

			return ShellStatus.CONTINUE;
		}

		env.writeln("Help command must have zero or one argument.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command help can be run with one or zero arguments");
		list.add("First argument must be one of the commands names");
		list.add("If there was zero arguments, list of all commands is written");
		list.add("If there is one argument, second arguments must be one of the commands");
		list.add("In that case value of description for given command is written");
		return list;
	}

}
