package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CDCommand;
import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * Implementation of environment used for working with user over command prompt
 * 
 * @author Matej Fureš
 *
 */
public class CommandPromptEnvironment implements Environment {
	/**
	 * Scanner used for reading from System.in
	 */
	private Scanner sc;

	/**
	 * Base directory used for commands
	 */
	private Path currentDirectory;

	/**
	 * Used for storing shared data
	 */
	private Map<String, Object> map;

	/**
	 * Map used for containing commands
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Multi line symbol. Default value is '|'
	 */
	private Character multilineSymbol = '|';

	/**
	 * Prompt symbol. Default value is '>'
	 */
	private Character promptSymbol = '>';

	/**
	 * More line symbol. Default value is '\'
	 */
	private Character morelinesSymbol = '\\';

	/**
	 * Creates an environment used for reading from command prompt
	 * 
	 * @throws IOException
	 */
	public CommandPromptEnvironment() throws IOException {
		writeln("Welcome to MyShell v 1.0");
		sc = new Scanner(System.in);
		commands = new TreeMap<>();
		currentDirectory = Paths.get(".").toRealPath();
		map = new HashMap<>();

		insertCommands();
	}

	/**
	 * Adds all commands to the environment
	 */
	private void insertCommands() {
		commands.put("symbol", new SymbolCommand());
		commands.put("help", new HelpCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("exit", new ExitCommand());
		commands.put("cat", new CatCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("copy", new CopyCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("tree", new TreeCommand());
		commands.put("pwd", new PwdCommand());
		commands.put("cd", new CDCommand());
		commands.put("pushd", new PushdCommand());
		commands.put("popd", new PopdCommand());
		commands.put("listd", new ListdCommand());
		commands.put("dropd", new DropdCommand());
		commands.put("massrename", new MassrenameCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		write(promptSymbol + " ");

		StringBuilder sb = new StringBuilder();
		String current = sc.nextLine();

		while (current.endsWith(" " + morelinesSymbol)) {
			sb.append(current.substring(0, current.length() - 1));
			write(multilineSymbol + " ");
			current = sc.nextLine();
		}

		sb.append(current);

		return sb.toString();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		Objects.requireNonNull(path);

		Path old = currentDirectory;
		try {
			currentDirectory = path.toRealPath();
		} catch (Exception e) {
			currentDirectory = old;
			writeln("Couldnt change active directory");
		}
	}

	@Override
	public Object getSharedData(String key) {
		return map.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key);

		map.put(key, value);
	}

}
