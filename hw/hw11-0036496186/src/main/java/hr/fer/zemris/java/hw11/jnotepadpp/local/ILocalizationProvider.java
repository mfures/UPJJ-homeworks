package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that defines localization provider as used in this application
 * 
 * @author matfures
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds localization listener to be notified when changes happen
	 * 
	 * @param l listener to be added
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes localization listener if it exist
	 * 
	 * @param l listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Returns value of string for given key in set localization
	 * 
	 * @param key of string
	 * @return string under key key
	 */
	String getString(String key);

	/**
	 * Returns value of current language
	 * 
	 * @return value of language
	 */
	String getCurrentLanguage();
	
	/**
	 * Setter for language. Acceptable values are "hr"->Croatian, "en"->English and
	 * "de"->German. Any other input throws an exception.
	 * 
	 * @param language to be set
	 * @throws IllegalArgumentException if language isn't supported
	 */
	void setLanguage(String language);
}
