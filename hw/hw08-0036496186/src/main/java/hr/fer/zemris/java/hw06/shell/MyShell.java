package hr.fer.zemris.java.hw06.shell;

/**
 * Runs shell program on command prompt environment
 * 
 * @author Matej Fureš
 *
 */
public class MyShell {
	/**
	 * Runs shell program. Program doesn't take arguments
	 * 
	 * @param args should be none
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Program should've been started without arguments.");
			System.exit(1);
		}

		String l;
		ShellCommand command;
		ShellStatus status;
		hr.fer.zemris.java.hw06.shell.Utility.NameAndArgs current;
		Environment environment = null;

		try {
			environment = new CommandPromptEnvironment();
		} catch (Exception e) {
			System.out.println("Couldnt create environment");
			System.exit(1);
		}
		do {
			l = environment.readLine();
			current = Utility.toCommandAndArgs(l);
			command = environment.commands().get(current.commandName);
			if (command == null) {
				environment.writeln(
						"Unknown command. For help, writh help. For help with some command, write help commandName.");
				status = ShellStatus.CONTINUE;
			} else {
				status = command.executeCommand(environment, current.args);
			}
		} while (status != ShellStatus.TERMINATE);
	}
}
