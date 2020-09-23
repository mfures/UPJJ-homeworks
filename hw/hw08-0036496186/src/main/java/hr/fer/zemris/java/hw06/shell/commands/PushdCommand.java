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
import hr.fer.zemris.java.hw06.shell.parsing.ArgumentParser;

public class PushdCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
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
			path = Utility.getPathIfDirectory(path.toString());
		} catch (Exception e) {
			env.writeln("Couldn't resolve given path.");
			return ShellStatus.CONTINUE;
		}

		Object el = env.getSharedData(Utility.SHARED_DATA_KEY);
		if (el == null) {
			Stack<Path> s = new Stack<Path>();
			s.push(env.getCurrentDirectory());
			env.setSharedData(Utility.SHARED_DATA_KEY, s);
		} else {
			((Stack<Path>) el).push(env.getCurrentDirectory());
		}

		env.setCurrentDirectory(path);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command pushd can be run with only one argument");
		list.add("First argument must be path to directory");
		list.add("Command saves active directory and sets active to given");
		return list;
	}

}
