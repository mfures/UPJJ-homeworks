package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Operator;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser that builds the accurate document model which represents the document
 * as provided by the user.
 * 
 * @author Matej Fureš
 *
 */
public class SmartScriptParser {
	/**
	 * Input that should be parsed
	 */
	private String input;

	/**
	 * DocumentNode that contains parsed input in other Nodes
	 */
	private DocumentNode documentNode;

	/**
	 * Constructor. Input string will be parsed. Input mustn't be null, exception is
	 * thrown otherwise. Exception is thrown if input isn't formated correctly.
	 * 
	 * @param input String to be parsed
	 * @throws NullPointerException       if input is null
	 * @throws SmartScriptParserException if input isn't formated correctly
	 */
	public SmartScriptParser(String input) {
		Objects.requireNonNull(input);

		this.input = input;
		documentNode = new DocumentNode();
		parse();
	}

	/**
	 * Returns documentNode for current parser
	 * 
	 * @return documentNode property
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Generates Document Nodes content. If input isn't in good format, an exception
	 * is thrown.
	 * 
	 * @throws SmartScriptParserException
	 */
	private void parse() {
		SmartScriptLexer lexer = null;
		try {
			lexer = new SmartScriptLexer(input);
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}

		SmartScriptToken currentToken = null;
		ObjectStack activeForLoopNodes = new ObjectStack();

		do {
			currentToken = getNextTokenFromLexer(lexer);

			if (currentToken.getType() == SmartTokenType.TEXT) {
				handleTextToken(activeForLoopNodes, currentToken);
				continue;
			}

			if (currentToken.getType() == SmartTokenType.TAG_OPENED) {
				handleOpenedBracesToken(lexer, currentToken, activeForLoopNodes);
				continue;
			}

			if (currentToken.getType() != SmartTokenType.EOF) {
				throw new SmartScriptParserException();
			}

		} while (currentToken.getType() != SmartTokenType.EOF);

		if (!activeForLoopNodes.isEmpty()) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Tries to get next Token from lexer. If it fails, it throws an exception
	 * 
	 * @param lexer whose next token we want to generate
	 * @return lexers next {@link SmartScriptToken} token
	 */
	private SmartScriptToken getNextTokenFromLexer(SmartScriptLexer lexer) {
		SmartScriptToken token = null;

		try {
			token = lexer.nextToken();
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}

		return token;
	}

	/**
	 * Determines next element in for echo node, if element can't be determined, an
	 * null is returned.
	 * 
	 * @param token to evaluate
	 * @param lexer used to get tokens
	 * @return next Element in echo node
	 * @throws SmartScriptParserException if String input isn't formated correctly.
	 * 
	 */
	private Element getNextElementInEchoNode(SmartScriptToken currentToken, SmartScriptLexer lexer) {
		if (currentToken.getType() == SmartTokenType.FUNCTION) {
			return new ElementFunction((String) currentToken.getValue());
		}

		if (currentToken.getType() == SmartTokenType.OPERATOR) {
			if (currentToken.getValue() == Operator.EQUAL_SIGN) {
				throw new SmartScriptParserException();
			}

			return new ElementOperator(((Operator) currentToken.getValue()).getOperation());
		}

		return getNextElementInForLoopNode(currentToken, lexer);
	}

	/**
	 * Determines next element in for loop node, if element can't be determined, an
	 * null is returned.
	 * 
	 * @param token to evaluate
	 * @return next Element in for loop
	 * @throws SmartScriptParserException if String input isn't formated correctly.
	 * 
	 */
	private Element getNextElementInForLoopNode(SmartScriptToken currentToken, SmartScriptLexer lexer) {
		if (currentToken.getType() == SmartTokenType.VARIABLE) {
			return new ElementVariable((String) currentToken.getValue());
		}

		if (currentToken.getType() == SmartTokenType.STRING) {
			return new ElementString((String) currentToken.getValue());
		}

		if (currentToken.getType() == SmartTokenType.INTEGER) {
			return new ElementConstantInteger((int) currentToken.getValue());
		}

		if (currentToken.getType() == SmartTokenType.DOUBLE) {
			return new ElementConstantDouble((double) currentToken.getValue());
		}

		return null;
	}

	/**
	 * Method throws an exception if input element is == null
	 * 
	 * @param element to be null tested
	 * @return element if it's not null
	 * @throws SmartScriptParserException is thrown if element is null
	 */
	private Element requierNotNull(Element element) {
		if (element == null) {
			throw new SmartScriptParserException();
		}

		return element;
	}

	/**
	 * Handles input Token, when token is TEXT
	 * 
	 * @param stack stack of active for loops
	 * @param token text token to be handled
	 */
	private void handleTextToken(ObjectStack stack, SmartScriptToken token) {
		if (stack.isEmpty()) {
			documentNode.addChildNode(new TextNode((String) token.getValue()));
			return;
		}

		ForLoopNode node = (ForLoopNode) stack.pop();
		node.addChildNode(new TextNode((String) token.getValue()));
		stack.push(node);
	}

	/**
	 * Handles input Token, when token is "{$"
	 * 
	 * @param lexer              used to get token
	 * @param currentToken       current token
	 * @param activeForLoopNodes Stack of active for loops
	 * @throws SmartScriptParserException if input wasn't in good format
	 */
	private void handleOpenedBracesToken(SmartScriptLexer lexer, SmartScriptToken currentToken,
			ObjectStack activeForLoopNodes) {

		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		currentToken = getNextTokenFromLexer(lexer);

		if (currentToken.getType() == SmartTokenType.OPERATOR) {
			if ((Operator) currentToken.getValue() == Operator.EQUAL_SIGN) {
				handleEqualSignTag(activeForLoopNodes, lexer, currentToken);
				return;
			}
		}

		if (currentToken.getType() == SmartTokenType.VARIABLE) {
			if (((String) currentToken.getValue()).toUpperCase().equals("FOR")) {
				handleForTag(activeForLoopNodes, lexer, currentToken);
				return;
			}

			if (((String) currentToken.getValue()).toUpperCase().equals("END")) {
				handleEndTag(activeForLoopNodes, lexer, currentToken);
				return;
			}
		}

		throw new SmartScriptParserException();
	}

	/**
	 * Handles input Token, when token is END
	 * 
	 * @param lexer              used to get token
	 * @param currentToken       current token
	 * @param activeForLoopNodes Stack of active for loops
	 * @throws SmartScriptParserException if input wasn't in good format
	 */
	private void handleEndTag(ObjectStack activeForLoopNodes, SmartScriptLexer lexer, SmartScriptToken currentToken) {
		if (activeForLoopNodes.isEmpty()) {
			throw new SmartScriptParserException();
		}

		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		currentToken = getNextTokenFromLexer(lexer);

		if (currentToken.getType() != SmartTokenType.TAG_CLOSED) {
			throw new SmartScriptParserException();
		}

		lexer.setState(SmartScriptLexerState.BASIC_TEXT);

		ForLoopNode node = (ForLoopNode) activeForLoopNodes.pop();

		if (!activeForLoopNodes.isEmpty()) {
			ForLoopNode parent = (ForLoopNode) activeForLoopNodes.pop();
			parent.addChildNode(node);
			activeForLoopNodes.push(parent);
			return;
		}

		documentNode.addChildNode(node);
	}

	/**
	 * Handles input Token, when token is For
	 * 
	 * @param lexer              used to get token
	 * @param currentToken       current token
	 * @param activeForLoopNodes Stack of active for loops
	 * @throws SmartScriptParserException if input wasn't in good format
	 */
	private void handleForTag(ObjectStack activeForLoopNodes, SmartScriptLexer lexer, SmartScriptToken currentToken) {
		currentToken = getNextTokenFromLexer(lexer);

		if (currentToken.getType() != SmartTokenType.VARIABLE) {
			throw new SmartScriptParserException();
		}

		ElementVariable variable = new ElementVariable((String) currentToken.getValue());

		Element startExpression = requierNotNull(
				getNextElementInForLoopNode(currentToken = getNextTokenFromLexer(lexer), lexer));

		Element endExpression = requierNotNull(
				getNextElementInForLoopNode(currentToken = getNextTokenFromLexer(lexer), lexer));

		currentToken = getNextTokenFromLexer(lexer);
		if (currentToken.getType() == SmartTokenType.TAG_CLOSED) {
			lexer.setState(SmartScriptLexerState.BASIC_TEXT);

			ForLoopNode node = new ForLoopNode(variable, startExpression, endExpression, null);
			activeForLoopNodes.push(node);
			return;
		}

		Element stepExpression = requierNotNull(getNextElementInForLoopNode(currentToken, lexer));

		currentToken = getNextTokenFromLexer(lexer);
		if (currentToken.getType() != SmartTokenType.TAG_CLOSED) {
			throw new SmartScriptParserException();
		}
		lexer.setState(SmartScriptLexerState.BASIC_TEXT);

		ForLoopNode node = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		activeForLoopNodes.push(node);
	}

	/**
	 * Handles input Token, when token is "="
	 * 
	 * @param lexer              used to get token
	 * @param currentToken       current token
	 * @param activeForLoopNodes Stack of active for loops
	 * @throws SmartScriptParserException if input wasn't in good format
	 */
	private void handleEqualSignTag(ObjectStack activeForLoopNodes, SmartScriptLexer lexer,
			SmartScriptToken currentToken) {

		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		currentToken = getNextTokenFromLexer(lexer);
		while (true) {
			if (currentToken.getType() == SmartTokenType.TAG_CLOSED) {
				break;
			}
			Element element = requierNotNull(getNextElementInEchoNode(currentToken, lexer));
			elements.add(element);

			currentToken = getNextTokenFromLexer(lexer);
		}

		Element[] newElements = Arrays.copyOf(elements.toArray(), elements.toArray().length, Element[].class);

		EchoNode node = new EchoNode(newElements);

		if (activeForLoopNodes.isEmpty()) {
			documentNode.addChildNode(node);
		} else {
			ForLoopNode loopNode = (ForLoopNode) activeForLoopNodes.pop();
			loopNode.addChildNode(node);
			activeForLoopNodes.push(loopNode);
		}

		lexer.setState(SmartScriptLexerState.BASIC_TEXT);
	}
}
