package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that prints parameters in table
 * 
 * @author matfures
 *
 */
public class EchoParams implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write("<html>\r\n\t<body>\r\n\t\t<table border=\"1\">\r\n");
			context.write("\t\t\t<tr>\r\n");
			context.write("\t\t\t\t<th>key</th>\r\n");
			context.write("\t\t\t\t<th>value</th>\r\n");
			context.write("\t\t\t</tr>\r\n");
			for (String key : context.getParameterNames()) {
				context.write("\t\t\t<tr>\r\n");
				context.write("\t\t\t\t<td>"+key+"</td>\r\n");
				context.write("\t\t\t\t<td>"+context.getParameter(key)+"</td>\r\n");
				context.write("\t\t\t</tr>\r\n");
			}

			context.write("\t\t</table>\r\n\t</body>\r\n</html>");
		} catch (IOException ex) {
// Log exception to servers log...
			ex.printStackTrace();
		}
	}
}
