package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action that handles opening empty documents
 * 
 * @author matfures
 *
 */
public class CreateEmptyDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "new";

	/**
	 * JNotepadPP that owns this action
	 */
	private JNotepadPP notepad;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param key      for localization
	 * @param provider for localization
	 * @throws NullPointerException if arguments are null
	 */
	public CreateEmptyDocumentAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		notepad.getEditor().createNewDocument();
	}
}
