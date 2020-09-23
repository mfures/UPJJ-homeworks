package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Decorator over JMenu that provides functionality with localization
 * 
 * @author matfures
 *
 */
public class LocalizableJMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * Key for localization
	 */
	private String key;

	/**
	 * Constructor
	 * 
	 * @param key      to be set
	 * @param provider for localization
	 */
	public LocalizableJMenu(String key, ILocalizationProvider provider) {
		super(provider.getString(key));
		this.key = key;

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(LocalizableJMenu.this.key));
			}
		});
	}
}
