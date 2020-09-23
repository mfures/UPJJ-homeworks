package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class VisitorDemo {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Incorrect number of arguments");
			System.exit(-1);
		}
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Unable to open document!");
			System.exit(-1);
		}

		SmartScriptParser p = null;
		try {
			p = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			String text = node.getText();
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) == '{') {
					sb.append('\\');
				}

				if (text.charAt(i) == '\\') {
					sb.append('\\');
				}

				sb.append(String.valueOf(text.charAt(i)));
			}

			System.out.print(sb.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String s = "{$ FOR";

			s += " " + node.getVariable().toString();
			s += " " + node.getStartExpression().toString();
			s += " " + node.getEndExpression().toString();
			s += node.getStepExpression() == null ? "" : " " + node.getStepExpression().toString();

			s += " $}";

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

			s += "{$ END $}";
			System.out.print(s);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			String s = "{$ =";

			for (int i = 0; i < node.getElements().length; i++) {
				s += " " + node.getElements()[i].toString();
			}
			s += " $}";

			System.out.print(s);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			String originalDocument = "";

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

			System.out.print(originalDocument);
		}
	}
}
