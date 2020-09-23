package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parsing.ArgumentParser;

/**
 * Implementation of command that creates a directory
 * 
 * @author Matej Fureš
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		Path path = null;

		try {
			ArgumentParser parser = new ArgumentParser(arguments);
			if (parser.numOfArgs() != 1) {
				env.writeln("1 or argument expected, " + parser.numOfArgs() + " were given.");
				return ShellStatus.CONTINUE;
			}

			path = env.getCurrentDirectory().resolve(parser.get(0));
			if (Files.exists(path)) {
				env.writeln("File already exist.");
				return ShellStatus.CONTINUE;
			}
		} catch (Exception e) {
			env.writeln("Couldn't resolve given path.");
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
