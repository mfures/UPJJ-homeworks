package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Implementation of command that lists contents of given directory
 * 
 * @author Matej
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		if (arguments.isEmpty()) {
			env.writeln("Ls command needs at least 1 argument");
			return ShellStatus.CONTINUE;
		}

		String[] args = arguments.split("\\s+");
		if (args.length > 1) {
			env.writeln("Ls command needs at most 1 argument");
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
			StringBuilder currentLine = new StringBuilder();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Path p : Files.newDirectoryStream(path)) {
				currentLine.append(tagsForPath(p));
				currentLine.append(String.format("%10d", Files.size(p)));
				currentLine.append(' ');
				currentLine.append(sdf.format(new Date(
						Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
								.readAttributes().creationTime().toMillis()))
						.toString());
				currentLine.append(' ');
				currentLine.append(p.getFileName());
				
				env.writeln(currentLine.toString());
				currentLine.setLength(0);
			}
		} catch (Exception e) {
			env.writeln("Couldnt itterate trough file");
			return ShellStatus.CONTINUE;
		}

		return null;
	}

	/**
	 * The output consists of 4 columns. First column indicates if current object is
	 * directory (d), readable (r), writable (w) and executable (x).
	 * 
	 * @param path to analyze
	 * @return output
	 */
	private String tagsForPath(Path path) {
		StringBuilder sb = new StringBuilder();

		if (Files.isDirectory(path)) {
			sb.append('d');
		} else {
			sb.append('-');
		}

		if (Files.isReadable(path)) {
			sb.append('r');
		} else {
			sb.append('-');
		}

		if (Files.isWritable(path)) {
			sb.append('w');
		} else {
			sb.append('-');
		}

		if (Files.isExecutable(path)) {
			sb.append('x');
		} else {
			sb.append('-');
		}

		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command ls can be run with only one argument");
		list.add("First argument must be path to directory");
		list.add("Command writes details about contents of given directory");
		return list;
	}

}
