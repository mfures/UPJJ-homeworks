package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {
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

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like
		// original content of docBody

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		System.out.println(originalDocumentBody.equals(createOriginalDocumentBody(parser2.getDocumentNode())));
	}

	/**
	 * Retrieves string values of all nodes in document and returns it as string
	 * 
	 * @param document to retrieve text from
	 * @return String representation of DocumentNode
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();
	}
}
