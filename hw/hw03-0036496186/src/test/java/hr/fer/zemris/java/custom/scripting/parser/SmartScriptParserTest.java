package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTest {

	@Test
	public void emptyDocument() {
		String document = loader("document01.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void someText() {
		String document = loader("document02.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void someMoreText() {
		String document = loader("document03.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void echoTag() {
		String document = loader("document04.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void forTag() {
		String document = loader("document05.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void emptyEchoTag() {
		String document = loader("document06.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void fourFors() {
		String document = loader("document07.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode node = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(node.toString());
		assertEquals(node.toString(), parser2.getDocumentNode().toString());

	}

	@Test
	public void missingEndTag() {
		String document = loader("document08.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	@Test
	public void extraEndTag() {
		String document = loader("document09.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	@Test
	public void onlyEndTag() {
		String document = loader("document10.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	@Test
	public void badString() {
		String document = loader("document11.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	@Test
	public void forTooManyArgs() {
		String document = loader("document12.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	@Test
	public void forTooFewArgs() {
		String document = loader("document13.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(document));
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
