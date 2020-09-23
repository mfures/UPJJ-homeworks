package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Decorator over {@link LocalizationProviderBridge} that adds functionality for
 * frames
 * 
 * @author matfures
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Constructor
	 * 
	 * @param parent to connect to
	 * @param frame  that is tracked
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
