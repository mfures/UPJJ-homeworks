package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Used for writing formated tree of files and directories from given starting
 * directory
 * 
 * @author Matej Fureš
 *
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("Tree command needs at least 1 argument");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");
		if (args.length > 1) {
			env.writeln("Tree command needs at most 1 argument");
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
			if (!Files.isDirectory(path)) {
				env.writeln("File isn't dictionary");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("File doesn't exist");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new FileVisitor<>() {
				private int offset = 0;

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					env.writeln("  ".repeat(offset) + dir.getFileName());
					offset++;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					env.writeln("  ".repeat(offset) + file.getFileName());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					offset--;
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (Exception e) {
			env.writeln("Couldn't complete processing tree");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command tree can be run with only one argument");
		list.add("First argument must be path to directory");
		list.add("Command writes details about contents of given directory and all child directories");
		return list;
	}

}
