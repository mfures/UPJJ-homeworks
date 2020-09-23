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
 * Action that handles informations
 * 
 * @author matfures
 *
 */
public class InfoAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "info";

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
	 * @param saveAs   for alternative action
	 * @throws NullPointerException if arguments are null
	 */
	public InfoAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		this.notepad = notepad;
		this.provider = provider;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var doc = notepad.getEditor().getCurrentDocument();
		if (doc == null) {
			JOptionPane.showMessageDialog(notepad, provider.getString("info_empty"));
			return;
		}

		String text = doc.getTextComponent().getText();
		int blanks = 0, lines = 0, length = 0;

		if (text != null) {
			char[] data = text.toCharArray();
			for (char c : data) {
				switch (c) {
				case '\n':
					lines++;
				case '\t':
				case ' ':
					blanks++;
				}
			}

			length = data.length;
		}

		JOptionPane.showMessageDialog(notepad,
				provider.getString("num_len") + length + ". " + provider.getString("num_nblank") + (length - blanks)
						+ ". " + provider.getString("num_line") + (lines + 1) + ".",
				provider.getString(KEY), JOptionPane.INFORMATION_MESSAGE);

	}
}
