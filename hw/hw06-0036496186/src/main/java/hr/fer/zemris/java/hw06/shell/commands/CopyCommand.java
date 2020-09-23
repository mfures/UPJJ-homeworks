package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Command used for coping files
 * 
 * @author Matej
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("Copy command needs at least 2 argument");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");
		if (args.length != 2) {
			env.writeln("Copy command needs 2 argument");
			return ShellStatus.CONTINUE;
		}

		Path path, dest;

		try {
			path = Utility.toPath(args[0]);
		} catch (Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		try {
			dest = Utility.toPath(args[1]);
		} catch (Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(path)) {
			if (Files.isDirectory(path)) {
				env.writeln("File is directory");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("File doesn't exist");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(dest)) {
			if (Files.isDirectory(dest)) {
				dest = dest.resolve(path.getFileName());
			} else {
				env.writeln("If you dont want to overrite given file, type N, otherwise file will be overwriten.");
				if (env.readLine().equals("N")) {
					env.writeln("File wasn't copied");
					return ShellStatus.CONTINUE;
				}
			}
		} else {
			try {
				Files.createDirectories(dest);
			} catch (Exception e) {
				env.writeln("Couldn't create directories");
				return ShellStatus.CONTINUE;
			}
			dest = dest.resolve(path.getFileName());
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(dest))) {
			byte[] buffer = new byte[Utility.DEFAULT_INPUT_SIZE];
			int d = 0;

			while (true) {
				d = is.read(buffer);

				if (d < 0) {
					break;
				}

				os.write(buffer, 0, d);
			}

		} catch (Exception e) {
			env.writeln("Couldn't read from file");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command copy can be run with only two arguments");
		list.add("First argument must be valid path to file you want to copy, not directory");
		list.add("If there were two arguments, second arguments must be valid path to destination");
		list.add("If second argument is directory, file is copied into it");
		list.add("If second argument is a file that exist, file is copied over it if user agrees");
		return list;
	}

}
