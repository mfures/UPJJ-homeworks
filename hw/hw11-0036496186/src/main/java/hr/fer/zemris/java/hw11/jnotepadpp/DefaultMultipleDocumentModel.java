package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Single document model over which is currently operated
	 */
	private SingleDocumentModel currentDocument;

	/**
	 * List containing single document models this pane contains
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();

	/**
	 * Tracks previous modification values per document model
	 */
	private List<MyBoolean> currentModificationValues = new ArrayList<>();

	/**
	 * List containing multiple document listeners
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Default name for unsaved tabs
	 */
	private final String KEY_TAB = "new_file";

	/**
	 * Constructor for default multiple document model
	 */
	public DefaultMultipleDocumentModel() {
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel prev = currentDocument;
				currentDocument = getSelectedIndex() == -1 ? null : documents.get(getSelectedIndex());
				currentDocumentChanged(prev, currentDocument);
			}
		});
	}

	/**
	 * Notifies all registered listeners that value of current document has changed
	 * 
	 * @param prev value of document
	 * @param curr value of document
	 */
	private void currentDocumentChanged(SingleDocumentModel prev, SingleDocumentModel curr) {
		listeners.forEach(x -> x.currentDocumentChanged(prev, curr));
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addModel(new DefaultSingleDocumentModel(null, null));
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		for (SingleDocumentModel doc : documents) {
			if (path.equals(doc.getFilePath())) {
				setSelectedIndex(documents.indexOf(doc));
				return doc;
			}
		}

		String text = null;
		try {
			text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new IllegalArgumentException("Couldn't read from file");
		}
		return addModel(new DefaultSingleDocumentModel(path, text));

	}

	/**
	 * Adds model to collection
	 * 
	 * @param model to be added
	 * @return model that was added
	 */
	private SingleDocumentModel addModel(SingleDocumentModel model) {
		if (model.getFilePath() == null) {
			model.setModified(true);
		}

		model.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int indexOf = documents.indexOf(model);
				if (currentModificationValues.get(indexOf).getBool() == model.isModified()) {
					return;
				}
				currentModificationValues.get(indexOf).setBool(model.isModified());

				if (model.isModified()) {
					setIconAt(indexOf, icon("tabIcons/circle-16-red.png"));
				} else {
					setIconAt(indexOf, icon("tabIcons/circle-16-green.png"));
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
				setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
			}
		});

		documents.add(model);
		currentModificationValues.add(new MyBoolean(model.isModified()));
		listeners.forEach(x -> x.documentAdded(model));

		if (model.getFilePath() == null) {
			addTab(LocalizationProvider.getInstance().getString(KEY_TAB), new JScrollPane(model.getTextComponent()));
			setIconAt(documents.size() - 1, icon("tabIcons/circle-16-red.png"));
			setToolTipTextAt(documents.indexOf(model), LocalizationProvider.getInstance().getString(KEY_TAB));

		} else {
			addTab(model.getFilePath().getFileName().toString(), new JScrollPane(model.getTextComponent()));
			setIconAt(documents.size() - 1, icon("tabIcons/circle-16-green.png"));
			setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
		}
		setSelectedIndex(documents.size() - 1);

		return model;
	}

	/**
	 * Reads input from disc and creates icon
	 * 
	 * @param location of resource
	 * @return icon
	 */
	private ImageIcon icon(String location) {
		try (InputStream is = this.getClass().getResourceAsStream(location)) {
			if (is == null) {
				throw new IllegalArgumentException("Resource doesn't exist");
			}

			return new ImageIcon(is.readAllBytes());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (Exception e) {
			throw new IllegalArgumentException("Couldn't read from file");
		}
	}

	/**
	 * @throws IllegalArgumentException if combination of model and path is invalid
	 * @throws IllegalStateException    if file is tried to save to null if its path
	 *                                  is null
	 * @throws IOException              if errors occur while writing
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath != null) {
			for (var doc : documents) {
				if (newPath.equals(doc.getFilePath())) {
					throw new IllegalArgumentException(
							"There exist another opened file, with different content and same path as new path");
				}
			}
		}

		boolean current = false;
		SingleDocumentModel old = null;
		if (model.getTextComponent().equals(currentDocument.getTextComponent())) {
			if (model.getFilePath() == null) {
				if (currentDocument.getFilePath() == null) {
					current = true;
				}
			} else if (model.getFilePath().equals(currentDocument.getFilePath())) {
				current = true;
			}
		}

		if (newPath == null) {
			if (model.getFilePath() == null) {
				throw new IllegalStateException("Tryed to save previously unsaved file to null location");
			}
		} else {
			model.setFilePath(newPath);
		}

		try {
			Files.write(model.getFilePath(), model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new IllegalStateException("Errors occur while writing file");
		}

		model.setModified(false);

		if (current) {
			listeners.forEach(x -> x.currentDocumentChanged(old, model));
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int indexOf = documents.indexOf(model);
		if (indexOf == -1) {
			return;
		}

		removeTabAt(indexOf);
		currentModificationValues.remove(indexOf);
		documents.remove(model);

		if (documents.size() != 0) {
			var old = currentDocument;
			currentDocument = documents.get(0);
			setSelectedIndex(0);
			listeners.forEach(x -> x.currentDocumentChanged(old, currentDocument));
		}

		listeners.forEach(x -> x.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Simple wrapper class over boolean
	 * 
	 * @author matfures
	 *
	 */
	private static class MyBoolean {
		/**
		 * Boolean value that is wrapped
		 */
		private boolean bool;

		/**
		 * Constructor
		 * 
		 * @param bool that is wrapped
		 */
		private MyBoolean(boolean bool) {
			this.bool = bool;
		}

		/**
		 * Getter for value
		 * 
		 * @return booleans value
		 */
		public boolean getBool() {
			return bool;
		}

		/**
		 * Setter for boolean
		 * 
		 * @param bool value to be set
		 */
		public void setBool(boolean bool) {
			this.bool = bool;
		}
	}
}
