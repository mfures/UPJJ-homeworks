package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.db.lexer.ComparisonOperator;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Parser for parsing query commands
 * 
 * @author Matej Fureš
 *
 */
public class QueryParser {
	/**
	 * String to be parsed
	 */
	private String input;

	/**
	 * List containing all conditional expressions in input
	 */
	private List<ConditionalExpression> query;

	/**
	 * Parses input string. If input is null, an exception is thrown. If input can't
	 * be parsed, an exception is thrown.
	 * 
	 * @param input to be parse
	 * @throws NullPointerException if input is null
	 * @throws QueryParserException if input can't be parsed
	 */
	public QueryParser(String input) {
		Objects.requireNonNull(input);

		this.input = input;
		query = new ArrayList<>();
		parseInput();
	}

	/**
	 * Method determines has parser parsed direct query, and if so return true,
	 * false otherwise
	 * 
	 * @return true if query was direct, false othwerwise
	 */
	public boolean isDirectQuery() {
		if (query.size() == 1) {
			ConditionalExpression exp = query.get(0);
			if (exp.getComparisonOperator().equals(ComparisonOperators.EQUALS)) {
				if (exp.getFieldGetter().equals(FieldValueGetters.JMBAG))
					return true;
			}
		}

		return false;
	}

	/**
	 * Returns value of Jmbag if query was direct. If it wasn't, an exception is
	 * thrown.
	 * 
	 * @return jmbag for direct query
	 * @throws IllegalStateException if query wasn't direct
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return query.get(0).getStringLiteral();
		}

		throw new IllegalStateException();
	}

	/**
	 * Returns array list containing all queries
	 * 
	 * @return list of queries
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> copy = new ArrayList<>(query.size());
		copy.addAll(query);
		
		return copy;
	}

	/**
	 * Parses input and generates ConditonalExpressions, if it can't parse an
	 * exception is thrown.
	 * 
	 * @throws QueryParserException if input can't be parsed
	 */
	private void parseInput() {
		QueryLexer lexer = null;

		try {
			lexer = new QueryLexer(input);
		} catch (Exception e) {
			throw new QueryParserException(e.getMessage());
		}

		Token token = tryToGetNextToken(lexer);

		while (token.getType() != TokenType.EOL) {
			if (token.getType() != TokenType.VARIABLE) {
				throw new QueryParserException("Conditonal expressions must start with variable.");
			}

			String variable = (String) token.getValue();

			requireValidVariableName(variable);
			IFieldValueGetter getter = generateValueGetter(variable);

			token = tryToGetNextToken(lexer);
			IComparisonOperator comp = getOperator(token);

			token = tryToGetNextToken(lexer);
			if (token.getType() != TokenType.STRING) {
				throw new QueryParserException("Litteral must be string.");
			}

			query.add(new ConditionalExpression(getter, ((String) token.getValue()), comp));
			token = tryToGetNextToken(lexer);

			if (token.getType() != TokenType.EOL) {
				requireAnd(token);

				token = tryToGetNextToken(lexer);
				if (token.getType() == TokenType.EOL) {
					throw new QueryParserException("AND must be followed by another conditonal expression.");
				}
			}

		}

		if (query.size() == 0) {
			throw new QueryParserException("Couldn't generate any conditonal expressions.");
		}
	}

	/**
	 * Checks if variable name is valid, and if it isn't, an exception is thrown.
	 * 
	 * @param s to be checked
	 * @throws QueryParserException if variable name is invalid
	 */
	private void requireValidVariableName(String s) {
		switch (s) {
		case "jmbag":
			return;

		case "lastName":
			return;

		case "firstName":
			return;
		}

		throw new QueryParserException("Invalid variable name");
	}

	/**
	 * Tryes to get next token, if it can't an exception is thrown.
	 * 
	 * @param lexer that generates next token
	 * @return next token
	 * @throws QueryParserException if can't get next token
	 */
	private Token tryToGetNextToken(QueryLexer lexer) {
		try {
			return lexer.nextToken();
		} catch (Exception e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Return correct IComparisonOperator in accordance with current token
	 * 
	 * @param token from whom operator is determined
	 * @return correct Comparison operator
	 * @throws QueryParserException if operator getter couldn't be resolved
	 * 
	 */
	private IComparisonOperator getOperator(Token token) {
		if (token.getType() == TokenType.VARIABLE) {
			if (((String) token.getValue()).equals("LIKE")) {
				return ComparisonOperators.LIKE;
			} else {
				throw new QueryParserException("Invalid operator in conditonal expression.");
			}
		} else {
			if (token.getType() == TokenType.OPERATOR) {
				switch ((ComparisonOperator) token.getValue()) {
				case LESSER:
					return ComparisonOperators.LESS;

				case EQUALS:
					return ComparisonOperators.EQUALS;

				case GREATER:
					return ComparisonOperators.GREATER;

				case GREATER_OR_EQUAL:
					return ComparisonOperators.GREATER_OR_EQUALS;

				case LESSER_OR_EQUAL:
					return ComparisonOperators.LESS_OR_EQUALS;

				case NOT_EQUALS:
					return ComparisonOperators.NOT_EQUALS;
				}
			}

			throw new QueryParserException("Invalid operator in conditonal expression.");
		}
	}

	/**
	 * Generates value getter for given string
	 * 
	 * @param variable to be processed
	 * @return valid value getter
	 * @throws QueryParserException if value getter couldn't be resolved
	 */
	private IFieldValueGetter generateValueGetter(String variable) {
		switch (variable) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		}

		throw new QueryParserException("Couldnt generate value getter");
	}

	/**
	 * Checks if next token is AND, if it isn't, an exception is thrown.
	 * 
	 * @param token to be tested
	 * @throws QueryParserException if token isn't AND
	 */
	private void requireAnd(Token token) {
		if (token.getType() == TokenType.VARIABLE) {
			if (((String) token.getValue()).toUpperCase().equals("AND")) {
				return;
			}
		}

		throw new QueryParserException("After conditional expresion AND is expected.");
	}
}
