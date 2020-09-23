package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Defines an model of object that manages multiple Single document models. Per
 * origin path can hold only one single document model, but can have many if set
 * path is null, and those are treated as unnamed
 * 
 * @author matfures
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new document
	 * 
	 * @return new document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns currently selected document
	 * 
	 * @return currently selected document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document from given path
	 * 
	 * @param path from which file is loaded
	 * @return new Single document model
	 * @throws NullPointerException if path is null
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves document to disc. If given path isn't null, path of document is changed
	 * to it after saving. Otherwise saves to original path
	 * 
	 * @param model   that is saved
	 * @param newPath path to which is saved, if null saved to origin
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes selected single document model
	 * 
	 * @param model that is closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds listener to this model
	 * 
	 * @param l that is added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes document listener from this model
	 * 
	 * @param l listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of stored documents
	 * 
	 * @return num of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns document at given index
	 * @param index of document
	 * @return
	 */
	SingleDocumentModel getDocument(int index);
}
