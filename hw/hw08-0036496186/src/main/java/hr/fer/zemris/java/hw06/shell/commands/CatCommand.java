package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;
import hr.fer.zemris.java.hw06.shell.parsing.ArgumentParser;

/**
 * Implementation of a command that writes content of given file to shell
 * 
 * @author Matej Fureš
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		Path path = null;
		Charset charset = null;

		try {
			ArgumentParser parser = new ArgumentParser(arguments);
			if (parser.numOfArgs() != 1 && parser.numOfArgs() != 2) {
				env.writeln("1 or 2 arguments expected, " + parser.numOfArgs() + " were given.");
				return ShellStatus.CONTINUE;
			}

			path = env.getCurrentDirectory().resolve(parser.get(0));
			path = Utility.getPathIfNotDirectory(path.toString());
			if (parser.numOfArgs() == 2) {
				if (Charset.isSupported(parser.get(1))) {
					charset = Charset.forName(parser.get(1));
				} else {
					env.writeln("Invalid charset given");
					return ShellStatus.CONTINUE;
				}

			} else {
				charset = Charset.defaultCharset();
			}
		} catch (Exception e) {
			env.writeln("Couldn't resolve given path.");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buffer = new byte[Utility.DEFAULT_INPUT_SIZE];
			int d = 0;

			while (true) {
				d = is.read(buffer);

				if (d < 0) {
					break;
				}

				env.write(new String(Arrays.copyOf(buffer, d), charset));
			}

		} catch (Exception e) {
			env.writeln("Couldn't read from file");
			return ShellStatus.CONTINUE;
		}

		env.writeln("");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command cat can be run with one or two arguments");
		list.add("First argument must be valid path to directory");
		list.add("If there was one argument, file is printed with default charset");
		list.add("If there were two arguments, second arguments must be one of the available charsets");
		list.add("In that case value file is printed using given charset");
		return list;
	}

}
