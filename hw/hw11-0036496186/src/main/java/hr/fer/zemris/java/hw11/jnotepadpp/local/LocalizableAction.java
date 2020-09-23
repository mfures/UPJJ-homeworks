package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.AbstractAction;

/**
 * Decorator over {@link AbstractAction} that allows for localization
 * 
 * @author matfures
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	/**
	 * Key for localization
	 */
	private String key;

	/**
	 * Constructor for {@link LocalizableAction} if any argument is null, an
	 * exception is thrown
	 * 
	 * @param key      to be set
	 * @param provider for localization
	 * @throws NullPointerException if any parameter is null
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = Objects.requireNonNull(key, "Key was null");

		putValue(NAME, Objects.requireNonNull(provider, "Provider was null").getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(key + "_sd"));

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(NAME, provider.getString(LocalizableAction.this.key));
				putValue(SHORT_DESCRIPTION, provider.getString(LocalizableAction.this.key + "_sd"));
			}
		});
	}
}