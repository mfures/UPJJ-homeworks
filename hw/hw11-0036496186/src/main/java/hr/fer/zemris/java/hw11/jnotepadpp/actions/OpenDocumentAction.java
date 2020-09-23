package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action that handles opening files from disc
 * 
 * @author matfures
 *
 */
public class OpenDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "open";

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
	public OpenDocumentAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		this.notepad = notepad;
		this.provider = provider;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getString(KEY));
		if (jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path toOpen = jfc.getSelectedFile().toPath();

		try {
			notepad.getEditor().loadDocument(toOpen);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(notepad, provider.getString("open_fail"));
		}
	}
}
