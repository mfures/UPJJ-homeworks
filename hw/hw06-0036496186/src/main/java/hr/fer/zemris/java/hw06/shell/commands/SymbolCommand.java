package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implements command that changes 1 of 3 symbols used in the shell. If given
 * symbol isn't one of used in the shell, message is writen to the user.
 * 
 * @author Matej Fureš
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("This command requiers atleast one argument.");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");
		if (args.length == 1) {
			return writeSymbol(env, args[0]);
		}

		if (args.length != 2) {
			env.writeln("This command requiers atmost two arguments.");
			return ShellStatus.CONTINUE;
		}

		return changeSymbol(env, args);
	}

	/**
	 * Updates given symbol to given value and writes message to user.
	 * 
	 * @param env  on which is written
	 * @param args commands arguments
	 * @return Always returns Continue
	 */
	private ShellStatus changeSymbol(Environment env, String[] args) {
		if (args[1].length() != 1) {
			env.writeln("Symbol should be given as C where C is a character.");
			return ShellStatus.CONTINUE;
		}

		Character newSymbol = args[1].charAt(0);

		switch (args[0]) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + newSymbol + "'");
			env.setPromptSymbol(newSymbol);
			return ShellStatus.CONTINUE;
		case "MORELINES":
			env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + newSymbol + "'");
			env.setMorelinesSymbol(newSymbol);
			return ShellStatus.CONTINUE;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + newSymbol + "'");
			env.setMultilineSymbol(newSymbol);
			return ShellStatus.CONTINUE;
		}

		env.writeln("Unsupported symbol. Use help for instructions.");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Writes symbol if it exist in shell, otherwise writes message to user
	 * 
	 * @param args should be symbol used in shell
	 * @param env  used for writing
	 * @return always returns Continue
	 */
	private ShellStatus writeSymbol(Environment env, String args) {
		switch (args) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			return ShellStatus.CONTINUE;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			return ShellStatus.CONTINUE;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Unsupported symbol. Use help for instructions.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command symbol can be run with one or two arguments");
		list.add("First argument must be one of the following:");
		list.add("PROMPT, MULTILINE or MORELINES");
		list.add("If there was only one arguments, value of given symbol is written to user");
		list.add("If there are two arguments, second arguments must be one char C");
		list.add("In that case value of given symbol is changed to given symbol");
		return list;
	}

}
