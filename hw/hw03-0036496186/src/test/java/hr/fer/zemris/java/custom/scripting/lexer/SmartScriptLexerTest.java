package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {
	@Test
	public void testJustEOF() {
		String document = loader("document01.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
	}

	@Test
	public void textTest() {
		String document = loader("document02.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TEXT, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

	}

	@Test
	public void textTestFromAgain() {
		String document = loader("document03.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TEXT, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

	}

	@Test
	public void basicEchoTest() {
		String document = loader("document04.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_OPENED, next.getType());
		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.OPERATOR, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.STRING, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.INTEGER, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.DOUBLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_CLOSED, next.getType());
		lexer.setState(SmartScriptLexerState.BASIC_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

	}

	@Test
	public void basicForTest() {
		String document = loader("document05.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_OPENED, next.getType());
		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.VARIABLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.VARIABLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.DOUBLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.DOUBLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.STRING, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_CLOSED, next.getType());
		lexer.setState(SmartScriptLexerState.BASIC_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TEXT, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_OPENED, next.getType());
		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.VARIABLE, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_CLOSED, next.getType());
		lexer.setState(SmartScriptLexerState.BASIC_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void emptyEchoTag() {
		String document = loader("document06.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken next = null;
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_OPENED, next.getType());
		lexer.setState(SmartScriptLexerState.TAG_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.OPERATOR, next.getType());
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.TAG_CLOSED, next.getType());
		lexer.setState(SmartScriptLexerState.BASIC_TEXT);
		assertNotNull(next = lexer.nextToken(), "Token was expected but null was returned.");
		assertEquals(SmartTokenType.EOF, next.getType());
		assertNull(next.getValue());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

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
