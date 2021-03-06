package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action that handles changing language to english
 * 
 * @author matfures
 *
 */
public class ChangeLanguageEnAction extends LocalizableAction {
	private static final long serialVersionUID = 7517840728976147335L;

	/**
	 * Key for localization
	 */
	private static final String KEY = "to_en";

	/**
	 * Provider of localization
	 */
	private ILocalizationProvider provider;

	/**
	 * Default constructor. See {@link LocalizableAction}
	 * 
	 * @param provider for localization
	 * @throws NullPointerException if arguments are null
	 */
	public ChangeLanguageEnAction(ILocalizationProvider provider) {
		super(KEY, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift E"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		this.provider = provider;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		provider.setLanguage("en");
	}
}
