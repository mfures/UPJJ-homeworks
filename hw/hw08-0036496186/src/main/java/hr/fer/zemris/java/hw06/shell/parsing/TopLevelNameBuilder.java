package hr.fer.zemris.java.hw06.shell.parsing;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.FilterResult;

/**
 * Top level name builder that runs execute commands of its other name builders
 * 
 * @author Matej Fureš
 *
 */
public class TopLevelNameBuilder implements NameBuilder {
	/**
	 * List of name builders
	 */
	List<NameBuilder> list;

	/**
	 * Creates top level name builder. list mustn't be null
	 * 
	 * @param list of name builders
	 * @throws NullPointerException if list is null
	 */
	public TopLevelNameBuilder(List<NameBuilder> list) {
		Objects.requireNonNull(list);

		this.list = list;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		Objects.requireNonNull(result);
		Objects.requireNonNull(sb);

		for (NameBuilder nb : list) {
			nb.execute(result, sb);
		}
	}

}
