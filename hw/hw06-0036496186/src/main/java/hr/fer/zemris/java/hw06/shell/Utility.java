package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class used for providing utility for shell program
 * 
 * @author Matej Fureš
 *
 */
public class Utility {
	/**
	 * Default size for input stream array
	 */
	public static final int DEFAULT_INPUT_SIZE = 4096;

	/**
	 * Splits given string to format commandName and arguments
	 * 
	 * @param input string to be parsed
	 * @return input in given format
	 * @throws NullPointerException if input is null
	 */
	public static NameAndArgs toCommandAndArgs(String input) {
		Objects.requireNonNull(input);

		int pos = input.indexOf(' ');

		if (pos == -1) {
			return new NameAndArgs(input, "");
		}

		String commandName = input.substring(0, pos);
		String arguments = input.substring(pos + 1, input.length());

		return new NameAndArgs(commandName, arguments);
	}

	/**
	 * Parses input string and returns valid path, if it exist. If valid path can't
	 * be created, an exception is thrown. If arg is null, exception is thrown
	 * 
	 * @param arg to be parsed
	 * @return path version of arg
	 * @throws IllegalArgumentException if arg is invalid
	 * @throws NullPointerException     if arg is null
	 */
	public static Path toPath(String arg) {
		Objects.requireNonNull(arg);

		if (arg.isEmpty()) {
			throw new IllegalArgumentException("Given argument is empty");
		}
		
		if(arg.length()==1) {
			try {
				return Paths.get(arg);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid path.");
			}
		}

		if (arg.startsWith("\"")) {
			if (!arg.endsWith("\"")) {
				try {
					return Paths.get(arg);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid path.");
				}
			}
		} else {
			try {
				return Paths.get(arg);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid path.");
			}
		}

		char[] data = arg.substring(1, arg.length() - 1).toCharArray();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < data.length; i++) {
			if(data[i]=='"') {
				throw new IllegalArgumentException("Qoutations must be escaped in string mode");
			}
			
			if (data[i] == '\\') {
				if (i + 1 < data.length) {
					i++;
					if (data[i] == '\\') {
						sb.append('\\');
					} else if (data[i] == '"') {
						sb.append('"');
					} else {
						sb.append('\\');
						sb.append(data[i]);
					}
				} else {
					sb.append('\\');
				}
			} else {
				sb.append(data[i]);
			}
		}

		try {
			return Paths.get(sb.toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid path.");
		}
	}
	

	/**
	 * Class used for storing results
	 * 
	 * @author Matej Fureš
	 *
	 */
	public static class NameAndArgs {
		/**
		 * Commands name
		 */
		String commandName;

		/**
		 * Arguments for command
		 */
		String args;

		/**
		 * Constructor. Arguments mustn't be null
		 * 
		 * @param commandName to be set
		 * @param args        to be set
		 * @throws NullPointerException if any argument is null
		 */
		public NameAndArgs(String commandName, String args) {
			Objects.requireNonNull(args);
			Objects.requireNonNull(commandName);
			
			this.args = args;
			this.commandName = commandName;
		}
	}
}
