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
 * Action that handles closing documents
 * 
 * @author matfures
 *
 */
public class CloseDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "close";

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
	private Action save;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param key      for localization
	 * @param provider for localization
	 * @param saveAs   for alternative action
	 * @throws NullPointerException if arguments are null
	 */
	public CloseDocumentAction(ILocalizationProvider provider, JNotepadPP notepad, Action save) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

		this.notepad = notepad;
		this.provider = provider;
		this.save = save;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var doc = notepad.getEditor().getCurrentDocument();
		if (doc == null) {
			JOptionPane.showMessageDialog(notepad, provider.getString("close_empty"));
			return;
		}

		if (!doc.isModified()) {
			notepad.getEditor().closeDocument(doc);
			return;
		}
		
		String[] options = new String[] { provider.getString("yes"), provider.getString("no") };
		if (JOptionPane.showOptionDialog(notepad, provider.getString("to_save_long"), provider.getString("to_save"),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]) == JOptionPane.YES_OPTION) {

			save.actionPerformed(e);
		}

		notepad.getEditor().closeDocument(doc);
	}
}
