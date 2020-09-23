package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing a single for-loop construct. It inherits from Node class
 * 
 * @author Matej Fureš
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Nodes ElementVariable variable
	 */
	private ElementVariable variable;

	/**
	 * Nodes start expression Element
	 */
	private Element startExpression;

	/**
	 * Nodes end expression Element
	 */
	private Element endExpression;

	/**
	 * Nodes step expression Element that can be null
	 */
	private Element stepExpression;

	/**
	 * Constructor. All variables except stepExpression mustn't be null, if any is
	 * an exception is thrown.
	 * 
	 * @param variable        variable to be set
	 * @param startExpression startExpression to be set
	 * @param endExpression   endExpression to be set
	 * @param stepExpression  stepExpressionto be set
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		Objects.requireNonNull(variable);
		Objects.requireNonNull(startExpression);
		Objects.requireNonNull(endExpression);

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Method returns Nodes variable property
	 * 
	 * @return variable property
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Method returns Nodes startExpression property
	 * 
	 * @return startExpression property
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Method returns Nodes endExpression property
	 * 
	 * @return endExpression property
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Method returns Nodes stepExpression property
	 * 
	 * @return stepExpression property
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Method to prints object Node in string representation
	 * 
	 * @return String representation of Node
	 */
	@Override
	public String toString() {
		String s = "{$ FOR";

		s += " " + getVariable().toString();
		s += " " + getStartExpression().toString();
		s += " " + getEndExpression().toString();
		s += getStepExpression() == null ? "" : " " + getStepExpression().toString();

		s += " $}";

		for (int i = 0; i < numberOfChildren(); i++) {
			s += getChild(i).toString();
		}

		s += "{$ END $}";
		return s;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
