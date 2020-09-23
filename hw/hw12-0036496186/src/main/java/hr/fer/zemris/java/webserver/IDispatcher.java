package hr.fer.zemris.java.webserver;

/**
 * Dispatcher interface
 * @author matfures
 *
 */
public interface IDispatcher {
	/**
	 * Used for dispatching request for some path
	 * @param urlPath to execute
	 * @throws Exception if errors occurred
	 */
	void dispatchRequest(String urlPath) throws Exception;
}