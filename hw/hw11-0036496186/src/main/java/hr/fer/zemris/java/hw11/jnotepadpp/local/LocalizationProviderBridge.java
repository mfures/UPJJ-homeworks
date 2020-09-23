package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

/**
 * Decorator over {@link AbstractLocalizationProvider}
 * 
 * @author matfures
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Is this {@link LocalizationProvider} connected to next one
	 */
	private boolean connected;

	/**
	 * Listener for this provider. Notifies all registered listeners on changes
	 */
	private ILocalizationListener listener = () -> fire();

	/**
	 * Parent for asking
	 */
	private ILocalizationProvider parent;

	/**
	 * Constructor. Parent mustn't be null
	 * 
	 * @param parent to connect to
	 * @throws NullPointerException if parent is null
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = Objects.requireNonNull(parent);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * Connects listener to parent
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}

	/**
	 * Disconnects listener from parent
	 */
	public void disconnect() {
		if (connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}

	@Override
	public void setLanguage(String language) {
		parent.setLanguage(language);
	}
}
