package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {
	/**
	 * Top level node
	 */
	private DocumentNode documentNode;

	/**
	 * Context to which is written
	 */
	private RequestContext requestContext;

	/**
	 * Holds nodes and elements
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Runs script
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (Exception e) {
				throw new RuntimeException("Couldn't write to stream.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper step = new ValueWrapper(node.getStepExpression().asText());

			String key = node.getVariable().asText();

			multistack.push(key, start);

			while (!(multistack.peek(key).numCompare(end.getValue()) > 0)) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				multistack.peek(key).add(step.getValue());
			}

			multistack.pop(key);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> stack = new Stack<>();

			for (Element e : node.getElements()) {
				if (e instanceof ElementConstantDouble) {
					stack.push(new ValueWrapper(((ElementConstantDouble) e).getValue()));
				} else if (e instanceof ElementConstantInteger) {
					stack.push(new ValueWrapper(((ElementConstantInteger) e).getValue()));
				} else if (e instanceof ElementString) {
					stack.push(new ValueWrapper(((ElementString) e).getValue()));
				} else if (e instanceof ElementVariable) {
					stack.push(new ValueWrapper(multistack.peek(e.asText()).getValue()));
				} else if (e instanceof ElementOperator) {
					handleOperator((ElementOperator) e, stack);
				} else if (e instanceof ElementFunction) {
					handleFunction(((ElementFunction) e).getName(), stack);
				}
			}

			writeStack(stack);
		}

		/**
		 * Writes all elements on stack in reverse order
		 * @param stack to be written out
		 */
		private void writeStack(Stack<ValueWrapper> stack) {
			if(stack.isEmpty()) {
				return;
			}
			
			ValueWrapper val=stack.pop();
			writeStack(stack);
			try {
				requestContext.write(val.toString());
			} catch (IOException e) {
				throw new RuntimeException("Couldnt write to stream");
			}
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

		/**
		 * Handles input function element
		 * 
		 * @param function name
		 * @param stack    of values
		 * @throws RuntimeException if function is unsupported
		 */
		private void handleFunction(String function, Stack<ValueWrapper> stack) {
			switch (function) {
			case "sin": {
				ValueWrapper x = stack.pop();
				ValueWrapper r = new ValueWrapper(Math.sin(Math.toRadians(Double.valueOf(x.toString()))));
				stack.push(r);
			}
				break;

			case "decfmt": {
				ValueWrapper f = stack.pop();
				ValueWrapper x = stack.pop();
				DecimalFormat form = new DecimalFormat(f.toString());
				stack.push(new ValueWrapper(form.format(x.getValue())));
			}
				break;

			case "dup": {
				ValueWrapper f = stack.pop();
				stack.push(f);
				stack.push(f);
			}
				break;

			case "swap": {
				ValueWrapper f = stack.pop();
				ValueWrapper e = stack.pop();

				stack.push(f);
				stack.push(e);
			}
				break;

			case "setMimeType": {
				ValueWrapper f = stack.pop();
				requestContext.setMimeType(f.toString());
			}
				break;

			case "paramGet": {
				ValueWrapper dv = stack.pop();
				ValueWrapper name = stack.pop();
				Object value = requestContext.getParameter(name.toString());
				value = value == null ? dv.toString() : value;
				stack.push(new ValueWrapper(value));
			}
				break;

			case "pparamGet": {
				ValueWrapper dv = stack.pop();
				ValueWrapper name = stack.pop();
				Object value = requestContext.getPersistentParameter(name.toString());
				value = value == null ? dv.toString() : value;
				stack.push(new ValueWrapper(value));
			}
				break;

			case "pparamSet": {
				ValueWrapper name = stack.pop();
				ValueWrapper value = stack.pop();
				requestContext.setPersistentParameter(name.toString(), value.toString());
			}
				break;

			case "pparamDel": {
				ValueWrapper name = stack.pop();
				requestContext.removePersistentParameter(name.toString());
			}
				break;

			case "tparamGet": {
				ValueWrapper dv = stack.pop();
				ValueWrapper name = stack.pop();
				Object value = requestContext.getTemporaryParameter(name.toString());
				value = value == null ? dv.toString() : value;
				stack.push(new ValueWrapper(value));
			}
				break;

			case "tparamSet": {
				ValueWrapper name = stack.pop();
				ValueWrapper value = stack.pop();
				requestContext.setTemporaryParameter(name.toString(), value.toString());
			}
				break;

			case "tparamDel": {
				ValueWrapper name = stack.pop();
				requestContext.removeTemporaryParameter(name.toString());
			}
				break;

			default:
				throw new RuntimeException("Unsupported function");
			}
		}

		/**
		 * Handles operator element
		 * 
		 * @param op    element
		 * @param stack with values
		 * @throws RuntimeException if operator isn't supported
		 */
		private void handleOperator(ElementOperator op, Stack<ValueWrapper> stack) {
			ValueWrapper v1 = stack.pop();
			ValueWrapper v2 = stack.pop();

			switch (op.asText()) {
			case "+":
				v1.add(v2.getValue());
				break;
			case "-":
				v1.subtract(v2.getValue());
				break;
			case "*":
				v1.multiply(v2.getValue());
				break;
			case "/":
				v1.divide(v2.getValue());
				break;
			default:
				throw new RuntimeException("Unsupported operator in echo tag");
			}

			stack.push(v1);
		}
	};

	/**
	 * Constructor that sets up script engine
	 * 
	 * @param documentNode   to be executed
	 * @param requestContext to execute node on
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		multistack = new ObjectMultistack();
	}

	/**
	 * Executes given document node
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}