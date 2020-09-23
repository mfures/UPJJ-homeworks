package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that sets background color for home page
 * 
 * @author matfures
 *
 */
public class BgColorWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		String color = context.getParameter("bgcolor");
		boolean validColor = true;

		if (color != null) {
			if (color.length() == 6) {
				for (char c : color.toCharArray()) {
					if (!isValidHexDigit(c)) {
						validColor = false;
						break;
					}
				}
			} else {
				validColor = false;
			}
		} else {
			validColor = false;
		}

		if (validColor) {
			context.setPersistentParameter("bgcolor", color.toUpperCase());
		}

		context.setMimeType("text/html");
		try {
			context.write("<html>\r\n\t<body>\r\n");
			context.write("\t\t<a href=\"/index2.html\">Home</a>\r\n");
			context.write("\t\t<p>Background color is");
			context.write(validColor?"":" not");
			context.write(" set</p>\r\n");
			context.write("\t</body>\r\n</html>");
		} catch (IOException ex) {
// Log exception to servers log...
			ex.printStackTrace();
		}
	}

	/**
	 * Checks if given character is valid hex digit
	 * 
	 * @param c to be checked
	 * @return true if is valid
	 */
	private boolean isValidHexDigit(char c) {
		if (Character.isDigit(c)) {
			return true;
		}
		if (!Character.isLetter(c)) {
			return false;
		}

		c = Character.toUpperCase(c);

		switch (c) {
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
			return true;
		}

		return false;
	}
}
