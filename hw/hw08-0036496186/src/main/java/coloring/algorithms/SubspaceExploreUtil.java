package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Contains walking algorithms used for walking pictures pixels
 * 
 * @author Matej Fureš
 *
 */
public class SubspaceExploreUtil {
	/**
	 * Bfs algorithm implementation.
	 * 
	 * @param s0         start state
	 * @param process    to do with acceptable states
	 * @param succ       list of successors
	 * @param acceptable test if state is acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> open = new LinkedList<>();
		open.add(s0.get());
		S current;

		while (!open.isEmpty()) {
			current = open.get(0);
			open.remove(0);
			if (!acceptable.test(current)) {
				continue;
			}
			process.accept(current);
			for (S child : succ.apply(current)) {
				open.add(child);
			}
		}
	}

	/**
	 * Bfs algorithm implementation.
	 * 
	 * @param s0         start state
	 * @param process    to do with acceptable states
	 * @param succ       list of successors
	 * @param acceptable test if state is acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> open = new LinkedList<>();
		open.add(s0.get());
		Set<S> visited = new HashSet<>();
		visited.add(s0.get());

		S current;

		while (!open.isEmpty()) {
			current = open.get(0);
			open.remove(0);
			if (!acceptable.test(current)) {
				continue;
			}
			process.accept(current);
			for (S child : succ.apply(current)) {
				if (!visited.contains(child)) {
					open.add(child);
					visited.add(child);
				}
			}
		}
	}
}
