package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
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
public class SaveDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "save";

	/**
	 * JNotepadPP that owns this action
	 */
	private JNotepadPP notepad;

	/**
	 * Provider of localization
	 */
	private ILocalizationProvider provider;

	/**
	 * Alternative for special cases
	 */
	private Action saveAs;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param key      for localization
	 * @param provider for localization
	 * @param saveAs   for alternative action
	 * @throws NullPointerException if arguments are null
	 */
	public SaveDocumentAction(ILocalizationProvider provider, JNotepadPP notepad, Action saveAs) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		this.notepad = notepad;
		this.provider = provider;
		this.saveAs = saveAs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var doc = notepad.getEditor().getCurrentDocument();
		if (doc == null) {
			JOptionPane.showMessageDialog(notepad, provider.getString("save_empty"));
			return;
		}

		if (doc.getFilePath() == null) {
			String[] options = new String[] { provider.getString("yes"), provider.getString("no") };
			if (JOptionPane.showOptionDialog(notepad, provider.getString("to_save_as_long"),
					provider.getString("to_save_as"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]) != JOptionPane.YES_OPTION) {
				return;
			}

			saveAs.actionPerformed(e);
			return;
		}

		try {
			notepad.getEditor().saveDocument(doc, null);
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
