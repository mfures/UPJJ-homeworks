package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of {@link SingleDocumentModel} used in {@link JNotepadPP}
 * application
 * 
 * @author matfures
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Text component in current document
	 */
	private JTextArea textComponent;

	/**
	 * Current document origin path. If is null, file isn't saved on disc
	 */
	private Path filePath;

	/**
	 * Flag that stores information if this document has been changed
	 */
	private boolean modified;

	/**
	 * List that stores listeners for this document model
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param file path to file on disc or null
	 * @param text to be set on document
	 */
	public DefaultSingleDocumentModel(Path file, String text) {
		filePath = file;
		textComponent = new JTextArea(text);
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = Objects.requireNonNull(path);
		listeners.forEach(x -> x.documentFilePathUpdated((this)));

	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		listeners.forEach(x -> x.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
