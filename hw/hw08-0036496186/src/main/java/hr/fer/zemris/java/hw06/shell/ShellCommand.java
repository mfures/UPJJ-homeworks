package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Defines set of methods for commands used in shell
 * 
 * @author Matej Fureš
 *
 */
public interface ShellCommand {
	/**
	 * Method that executes given commands with given arguments in said environment.
	 * Throws an exception if any of arguments are null
	 * 
	 * @param env       in which command is executed
	 * @param arguments with which command is executed
	 * @return Shells next status
	 * @throws NullPointerException if env, or arguments are null
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Method returns name value of given command
	 * 
	 * @return commands name
	 */
	String getCommandName();

	/**
	 * Method returns list with string that describe what command does
	 * 
	 * @return list of strings that describe command
	 */
	List<String> getCommandDescription();
}
