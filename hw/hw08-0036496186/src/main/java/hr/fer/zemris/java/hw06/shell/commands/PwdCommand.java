package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PwdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);
		
		if(arguments.isEmpty()!=true) {
			env.writeln("Pwd command doesn't use arguments.");
		}else {
			env.writeln(env.getCurrentDirectory().toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command pwd doesn't take any arguments");
		list.add("Command writes details current directory");
		return list;
	}

}
