package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that calculates sum
 * 
 * @author matfures
 *
 */
public class SumWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		int a = 1;
		int b = 2;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (Exception ignored) {
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (Exception ignored) {
		}

		context.setTemporaryParameter("zbroj", String.valueOf(a + b));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("imgName", ((a + b) % 2 == 0) ? "/images/p.png" : "/images/n.png");

		try {
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
