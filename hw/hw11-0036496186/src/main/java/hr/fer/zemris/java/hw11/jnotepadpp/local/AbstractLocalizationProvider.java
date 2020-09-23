package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link ILocalizationProvider} that doesn't implement
 * getString method and handles listerners
 * 
 * @author matfures
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List containing all registered listeners
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();

	/**
	 * @throws NullPointerException if l is null
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * Notifies all registered listeners
	 */
	public void fire() {
		listeners.forEach(x -> x.localizationChanged());
	}
}