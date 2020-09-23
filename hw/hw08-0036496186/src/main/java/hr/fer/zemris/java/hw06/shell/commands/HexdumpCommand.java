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
 * Command implementation that dumps hex output format of given file
 * 
 * @author Matej Fureš
 *
 */
public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);
		
		Path path = null;

		try {
			ArgumentParser parser = new ArgumentParser(arguments);
			if (parser.numOfArgs() != 1) {
				env.writeln("1 or argument expected, " + parser.numOfArgs() + " were given.");
				return ShellStatus.CONTINUE;
			}

			path = env.getCurrentDirectory().resolve(parser.get(0));
			path = Utility.getPathIfNotDirectory(path.toString());
		} catch (Exception e) {
			env.writeln("Couldn't resolve given path.");
			return ShellStatus.CONTINUE;
		}

		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buffer = new byte[Utility.DEFAULT_INPUT_SIZE];
			int d = 0;

			while (true) {
				d = is.read(buffer);

				if (d < 0) {
					break;
				}

				formatOutputAsHex(sb1, sb2, sb3, buffer, d, env);
			}

			finaly(sb1, sb2, sb3, env);
		} catch (Exception e) {
			env.writeln("Couldn't read from file");
			System.out.println(e.getClass());
			return ShellStatus.CONTINUE;
		} finally{
			counter[0]=0;
			counter[1]=0;
			counter[2]=0;
			counter[3]=0;
		}

		return ShellStatus.CONTINUE;
	}

	private void finaly(StringBuilder sb1, StringBuilder sb2, StringBuilder sb3, Environment env) {
		if(sb3.length()==0) {
			return;
		}
		
		if(sb3.length()<8) {
			sb2.append("   ".repeat(8-sb3.length()));
			sb2.append("|  ");
			sb2.append("   ".repeat(7));
		}else if(sb3.length()==8) {
			sb2.append("|  ");
			sb2.append("   ".repeat(7));
		}else {
			sb2.append("   ".repeat(16-sb3.length()));
		}
		
		env.writeln(sb1.toString() + ":" + sb2.toString() + "| " + sb3.toString());
	}

	/**
	 * Counter for output
	 */
	private byte[] counter = { 0,0,0,0 };

	/**
	 * Formats data in given format
	 * 
	 * @param sb1    formats counter
	 * @param sb2    formats bytes
	 * @param sb3    formats input
	 * @param arr    with bytes
	 * @param arrLen length of arr
	 * @param env    where is written
	 */
	private void formatOutputAsHex(StringBuilder sb1, StringBuilder sb2, StringBuilder sb3, byte[] arr, int arrLen,
			Environment env) {

		if (sb1.length() == 0) {
			sb1.append(bytetohex(counter));
		}

		int arrCounter = 0;
		while (arrCounter < arrLen) {
			if (sb3.length() < 8 || sb3.length() > 8) {
				if (!(arr[arrCounter] < 32 || arr[arrCounter] > 127)) {
					sb2.append(" " + bytetohex(arr[arrCounter]));
					sb3.append(
							new String(Arrays.copyOfRange(arr, arrCounter, arrCounter + 1), Charset.forName("UTF-8")));
				} else {
					sb2.append(" " + bytetohex(arr[arrCounter]));
					sb3.append('.');
				}
			} else {
				if (!(arr[arrCounter] < 32 || arr[arrCounter] > 127)) {
					sb2.append("|" + bytetohex(arr[arrCounter]));
					sb3.append(
							new String(Arrays.copyOfRange(arr, arrCounter, arrCounter + 1), Charset.forName("UTF-8")));
				} else {
					sb2.append("|" + bytetohex(arr[arrCounter]));
					sb3.append('.');
				}
			}

			if (sb3.length() == 16) {
				env.writeln(sb1.toString() + ":" + sb2.toString() + "| " + sb3.toString());

				sb1.setLength(0);
				incCounter();
				sb1.append(bytetohex(counter));
				sb2.setLength(0);
				sb3.setLength(0);
			}

			arrCounter++;
		}
	}

	/**
	 * Increments counter. If maximum value was crossed(FFFFFFFF) counter goes back
	 * to 0
	 */
	private void incCounter() {
		counter[3] += 16;

		// This could've been done recursively, but since format was set to work with 4
		// bytes per line, this solution works faster. If format was of variable length
		// per line, i would've gone for recursive solution or some that goes trough
		// array using its length.

		if (counter[3] == 0) {
			counter[2]++;
			if (counter[2] == 0) {
				counter[1]++;
				if (counter[1] == 0) {
					counter[0]++;
				}
			}
		}

	}

	/**
	 * Creates an hex representation of given byte array. Array mustn't be null
	 * 
	 * @param array to represent as hex string
	 * @return string representation of array
	 * @throws NullPointerException if array is null
	 */
	public static String bytetohex(byte... array) {
		Objects.requireNonNull(array);

		StringBuilder sb = new StringBuilder();
		char tmp;
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		for (byte b : array) {
			tmp = digits[b & 0b1111];
			sb.append(digits[(b >> 4) & 0b1111]);
			sb.append(tmp);
		}

		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command hexdump can be run with only one");
		list.add("First argument must be valid path to directory");
		list.add("If there was one argument, file is printed with in hexdump format");
		return list;
	}

}
