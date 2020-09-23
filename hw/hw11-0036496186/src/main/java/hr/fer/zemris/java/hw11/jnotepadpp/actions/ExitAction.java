package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action that handles exiting application
 * 
 * @author matfures
 *
 */
public class ExitAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "exit";

	/**
	 * JNotepadPP that owns this action
	 */
	private JNotepadPP notepad;

	/**
	 * Alternative for special cases
	 */
	private Action close;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param key      for localization
	 * @param provider for localization
	 * @param saveAs   for alternative action
	 * @throws NullPointerException if arguments are null
	 */
	public ExitAction(ILocalizationProvider provider, JNotepadPP notepad, Action close) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		this.notepad = notepad;
		this.close = close;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int numOfDoc = notepad.getEditor().getNumberOfDocuments();
		if (numOfDoc != 0) {
			notepad.getEditor().setSelectedIndex(0);
		}

		for (int i = 0; i < numOfDoc; i++) {
			close.actionPerformed(e);
		}

		notepad.dispose();
	}
}
