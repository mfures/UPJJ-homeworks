package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Defines a listener used for tracking documents in multiple document interface
 * used in this application
 * 
 * @author matfures
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Listens if current document has been changed
	 * 
	 * @param previousModel previous document
	 * @param currentModel  current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when new single document is added
	 * 
	 * @param model added document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when some single document model has been removed
	 * 
	 * @param model removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
