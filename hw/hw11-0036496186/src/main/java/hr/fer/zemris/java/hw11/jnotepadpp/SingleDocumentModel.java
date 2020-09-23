package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This interface defines a model of a single document that tracks its
 * modification and path of origin file on disc
 * 
 * @author matfures
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns text component of document
	 * 
	 * @return text component
	 */
	JTextArea getTextComponent();

	/**
	 * Returns files path on disc
	 * 
	 * @return path on disc
	 */
	Path getFilePath();

	/**
	 * Sets documents path to given path. Path mustn't be null
	 * 
	 * @param path to be set
	 * @throws NullPointerException if path is null
	 */
	void setFilePath(Path path);

	/**
	 * Method returns true if given document has been modified
	 * 
	 * @return true if document was modified, false otherwise
	 */
	boolean isModified();

	/**
	 * Sets modified value to given value
	 * 
	 * @param modified to be set
	 */
	void setModified(boolean modified);

	/**
	 * Adds a document listener to this document
	 * 
	 * @param l listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes document listener from this document if it is registered
	 * 
	 * @param l to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}