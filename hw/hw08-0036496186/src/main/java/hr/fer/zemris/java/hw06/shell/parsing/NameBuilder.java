package hr.fer.zemris.java.hw06.shell.parsing;

import hr.fer.zemris.java.hw06.shell.FilterResult;

/**
 * Builds string in its string builder by set rules
 * 
 * @author Matej Fureš
 *
 */
public interface NameBuilder {
	/**
	 * Builds correct string in given string builder. Arguments mustn't be null
	 * 
	 * @param result over which is working
	 * @param sb     string builder in which is written
	 * @throws NullPointerException if any argument is null
	 */
	void execute(FilterResult result, StringBuilder sb);

}
