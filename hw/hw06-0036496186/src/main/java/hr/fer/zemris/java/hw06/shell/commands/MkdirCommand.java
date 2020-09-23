package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Implementation of command that creates a directory
 * @author Matej Fureš
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("Mkdir command needs at least 1 argument");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");
		if (args.length > 1) {
			env.writeln("Mkdir command needs at most 1 argument");
			return ShellStatus.CONTINUE;
		}

		Path path;

		try {
			path = Utility.toPath(args[0]);
		} catch (Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(path)) {
			env.writeln("File already exist");
			return ShellStatus.CONTINUE;
		} 
		
		try {
			Files.createDirectories(path);
		} catch (Exception e) {
			env.writeln("Couldn't create directory");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command mkdir can be run with only one argument");
		list.add("First argument must be path to new directory");
		list.add("Command creates said directory if it doesn't already exsist");
		return list;
	}

}
