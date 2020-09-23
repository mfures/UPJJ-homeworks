package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * Query filter that filters records with conditional expressions list
 * 
 * @author Matej Fureš
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * List of conditions
	 */
	private List<ConditionalExpression> conditions;

	/**
	 * Constructor. Conditions mustn't be null
	 * 
	 * @param conditions to be checked
	 * @throws NullPointerException if conditions are null
	 */
	public QueryFilter(List<ConditionalExpression> conditions) {
		Objects.requireNonNull(conditions);
		
		this.conditions = conditions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression cond : conditions) {
			if (!cond.getComparisonOperator().satisfied(cond.getFieldGetter().get(record), cond.getStringLiteral())) {
				return false;
			}
		}

		return true;
	}

}
