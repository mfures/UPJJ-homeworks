package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Defines a listener used for tracking documents used in this application
 * 
 * @author matfures
 *
 */
public interface SingleDocumentListener {
	/**
	 * Listens if documents modify status has been changed
	 * 
	 * @param model that is listened to
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Listens if documents file path has been changed
	 * 
	 * @param model that is listened to
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}