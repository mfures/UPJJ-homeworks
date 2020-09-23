package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class ExecDemo {
	public static void main(String[] args) throws IOException {
		// String documentBody = new
		// String(Files.readAllBytes(Paths.get("osnovni.smscr")),
		// StandardCharsets.UTF_8);
		// Map<String, String> parameters = new HashMap<String, String>();
		// Map<String, String> persistentParameters = new HashMap<String, String>();
		// List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
//		// create engine and execute it
//		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
//				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
//
//		documentBody = new String(Files.readAllBytes(Paths.get("zbrajanje.smscr")), StandardCharsets.UTF_8);
//		parameters = new HashMap<String, String>();
//		persistentParameters = new HashMap<String, String>();
//		cookies = new ArrayList<RequestContext.RCCookie>();
//		parameters.put("a", "4");
//		parameters.put("b", "2");
//		// create engine and execute it
//		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
//				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/brojPoziva.smscr")),
				StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
}
