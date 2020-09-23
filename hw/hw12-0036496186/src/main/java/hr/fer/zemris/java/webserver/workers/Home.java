package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that build home page
 * 
 * @author matfures
 *
 */
public class Home implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		if(context.getPersistentParameter("bgcolor")!=null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		}else {
			context.setTemporaryParameter("background","7F7F7F");
		}

		try {
			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
