package hr.fer.zemris.java.webserver;

/**
 * Defines an worker that provides some utility for working on web
 * 
 * @author matfures
 *
 */
public interface IWebWorker {
	/**
	 * Method that processes request for certain context
	 * 
	 * @param context for which request is processed
	 * @throws Exception that is thrown if errors occurred
	 */
	public void processRequest(RequestContext context) throws Exception;
}