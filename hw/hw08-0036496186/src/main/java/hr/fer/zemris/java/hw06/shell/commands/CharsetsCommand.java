package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command that writes charstets to the user
 * 
 * @author Matej Fureš
 *
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);
		
		if(arguments.isEmpty()) {
			Charset.availableCharsets().forEach((k,v)->env.writeln(k));
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Command charsets should be run with no arguments");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command charsets can be run with only zero arguments");
		list.add("If there was zero arguments, list of all available charsets is written");
		return list;
	}

}
