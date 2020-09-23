package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Contains algorithms used for searching in state
 * 
 * @author Matej Fureš
 *
 */
public class SearchUtil {
	/**
	 * Bfs algorithm implementation.
	 * 
	 * @param s0   start state
	 * @param succ list of successors
	 * @param goal test if state is goal
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> open = new LinkedList<>();
		open.add(new Node<S>(null, s0.get(), 0));

		Node<S> current;

		while (!open.isEmpty()) {
			current = open.get(0);
			open.remove(0);
			if (goal.test(current.getState())) {
				return current;
			}

			for (Transition<S> trans : succ.apply(current.getState())) {
				open.add(new Node<S>(current, trans.getState(), trans.getCost() + current.getCost()));
			}
		}

		return null;
	}

	/**
	 * Bfsv algorithm implementation.
	 * 
	 * @param s0   start state
	 * @param succ list of successors
	 * @param goal test if state is goal
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> open = new LinkedList<>();
		open.add(new Node<S>(null, s0.get(), 0));
		Set<S> visited=new HashSet<S>();
		visited.add(s0.get());

		Node<S> current;

		while (!open.isEmpty()) {
			current = open.get(0);
			open.remove(0);
			if (goal.test(current.getState())) {
				return current;
			}

			for (Transition<S> trans : succ.apply(current.getState())) {
				if (!visited.contains(trans.getState())) {
					open.add(new Node<S>(current, trans.getState(), trans.getCost() + current.getCost()));
					visited.add(trans.getState());
				}
			}
		}

		return null;
	}
}
