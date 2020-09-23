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
import hr.fer.zemris.java.hw06.shell.parsing.ArgumentParser;

/**
 * Command used for coping files
 * 
 * @author Matej Fureš
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		Path source, dest;

		try {
			ArgumentParser parser = new ArgumentParser(arguments);
			if (parser.numOfArgs() != 2) {
				env.writeln("2 arguments expected, " + parser.numOfArgs() + " were given.");
				return ShellStatus.CONTINUE;
			}

			source = env.getCurrentDirectory().resolve(parser.get(0));
			source = Utility.getPathIfNotDirectory(source.toString());

			dest = env.getCurrentDirectory().resolve(parser.get(1));
			if (Files.exists(dest)) {
				if (Files.isDirectory(dest)) {
					if (Files.isDirectory(dest)) {
						dest = dest.resolve(source.getFileName());
					}
				} else {
					env.writeln("If you dont want to overrite given file, type N, otherwise file will be overwriten.");
					if (env.readLine().equals("N")) {
						env.writeln("File wasn't copied");
						return ShellStatus.CONTINUE;
					}
				}

			} else {
				if (Files.exists(dest.getParent())) {
					Files.createFile(dest);
				} else {
					env.writeln("Directory structure doesn't exist");
					return ShellStatus.CONTINUE;
				}
			}

		} catch (Exception e) {
			env.writeln("Couldn't resolve given paths.");
			return ShellStatus.CONTINUE;
		}
		
		if(dest.equals(source)) {
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(source));
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
