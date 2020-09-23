package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Complete implementation of {@link ILocalizationProvider} for providing
 * localization functionalities to application
 * 
 * @author matfures
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Current language
	 */
	private String language;

	/**
	 * Default language value
	 */
	private static final String DEFAULT_LANGUAGE = "en";

	/**
	 * Current resource bundle
	 */
	private ResourceBundle bundle;

	/**
	 * Instance of this class, only one
	 */
	private static LocalizationProvider instance;

	/**
	 * Private constructor. Values are set to default
	 */
	private LocalizationProvider() {
		language = DEFAULT_LANGUAGE;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.local",
				Locale.forLanguageTag(language));
	}

	/**
	 * Returns an instance of {@link LocalizationProvider}, there can be only one
	 * instance of it
	 * 
	 * @return instance of {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}

		return instance;
	}

	@Override
	public void setLanguage(String language) {
		if (language.equals("hr") || language.equals("en") || language.equals("de")) {
			this.language = language;
			bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.local",
					Locale.forLanguageTag(language));
			fire();
		} else {
			throw new IllegalArgumentException("Unsupported language");
		}
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}