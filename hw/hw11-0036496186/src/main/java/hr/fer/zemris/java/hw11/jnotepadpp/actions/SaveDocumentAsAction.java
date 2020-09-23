package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action that handles saving files to disc
 * 
 * @author matfures
 *
 */
public class SaveDocumentAsAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "save_as";

	/**
	 * JNotepadPP that owns this action
	 */
	private JNotepadPP notepad;

	/**
	 * Provider of localization
	 */
	private ILocalizationProvider provider;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param key      for localization
	 * @param provider for localization
	 * @throws NullPointerException if arguments are null
	 */
	public SaveDocumentAsAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		this.notepad = notepad;
		this.provider = provider;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var doc = notepad.getEditor().getCurrentDocument();
		if (doc == null) {
			JOptionPane.showMessageDialog(notepad, provider.getString("save_as_empty"));
			return;
		}

		Path openedFilePath;

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getString("save_as"));
		if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		openedFilePath = jfc.getSelectedFile().toPath();

		if (openedFilePath.equals(doc.getFilePath())) {
			openedFilePath = null;
		} else if (Files.exists(openedFilePath)) {
			String[] options = new String[] { provider.getString("yes"), provider.getString("no") };
			if (JOptionPane.showOptionDialog(notepad, provider.getString("overwrite_long"),
					provider.getString("overwrite"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]) != JOptionPane.YES_OPTION) {
				return;
			}
		}

		try {
			notepad.getEditor().saveDocument(doc, openedFilePath);
		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(notepad, provider.getString("file_present"), provider.getString("err"),
					JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(notepad, provider.getString("save_fail"), provider.getString("err"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

	}
}
