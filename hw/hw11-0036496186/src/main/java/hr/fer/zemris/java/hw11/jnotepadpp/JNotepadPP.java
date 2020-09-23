package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguageCroAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguageDeAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguageEnAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateEmptyDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Generic text editor that supports multiple tabs
 * 
 * @author matfures
 *
 */
public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Editor used for tabs
	 */
	private DefaultMultipleDocumentModel editor;

	/**
	 * Title of window
	 */
	private static final String TITLE = "JNotepad++";

	/**
	 * Provider for localization
	 */
	private ILocalizationProvider provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);;

	/**
	 * Timer for clock
	 */
	private Timer timer;

	/**
	 * Simple clock
	 */
	private JLabel clock;

	/**
	 * Tracks length of current document
	 */
	private JLabel length;

	/**
	 * Tracks status of current document
	 */
	private JLabel statusBar;

	/**
	 * Constructor that sets up the application
	 */
	private JNotepadPP() {
		setSize(1000, 600);
		setTitle(TITLE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		clock = new JLabel("", SwingConstants.RIGHT);
		updateClock = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				clock.setText(sdf.format(new Date(System.currentTimeMillis())));
			}
		};

		timer = new Timer(500, updateClock);
		timer.start();

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		editor = new DefaultMultipleDocumentModel();
		cp.add(new JScrollPane(editor), BorderLayout.CENTER);

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				int numOfDoc = JNotepadPP.this.getEditor().getNumberOfDocuments();
				for (int i = 0; i < numOfDoc; i++) {
					if (JNotepadPP.this.editor.getDocument(i).getFilePath() == null) {
						JNotepadPP.this.editor.setTitleAt(i, provider.getString("new_file"));
						JNotepadPP.this.editor.setToolTipTextAt(i, provider.getString("new_file"));
					}
				}

				setTitle(resolveTitle());
				updateLabels();
			}
		});

		length = new JLabel();
		statusBar = new JLabel();
		setUpEditorListeners();

		enableActions(false);
		updateLabels();

		configureActions();
		createMenus();
		createToolbar();
	}

	/**
	 * Sets up listeners on editor
	 */
	private void setUpEditorListeners() {
		editor.addMultipleDocumentListener(new MultipleDocumentListener() {
			/**
			 * Listener for current model
			 */
			SingleDocumentListener listener = new SingleDocumentListener() {

				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					updateLabels();
				}

				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
				}
			};

			/**
			 * Listener for carrot in current document
			 */
			ChangeListener cListener = new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					updateLabels();
					Caret c = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent().getCaret();
					enableActions(c.getDot() != c.getMark());
				}
			};

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				setTitle(resolveTitle());
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				setTitle(resolveTitle());
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				setTitle(resolveTitle());

				if (previousModel != null) {
					previousModel.removeSingleDocumentListener(listener);
					previousModel.getTextComponent().getCaret().removeChangeListener(cListener);
				}

				if (currentModel != null) {
					currentModel.addSingleDocumentListener(listener);
					currentModel.getTextComponent().getCaret().addChangeListener(cListener);
				}

				enableActions(false);
				updateLabels();
			}
		});
	}

	/**
	 * Updates length and status label
	 */
	private void updateLabels() {
		SingleDocumentModel current = editor.getCurrentDocument();
		int len = 0;
		int ln = 0, col = 0, sel = 0;
		if (current != null) {
			String text = current.getTextComponent().getText();
			if (text != null) {
				int pos = current.getTextComponent().getCaretPosition();
				len = text.length();
				try {
					ln = current.getTextComponent().getLineOfOffset(pos);
					col = pos - current.getTextComponent().getLineStartOffset(ln);
				} catch (BadLocationException ignorable) {
				}
				ln++;
				col++;
				sel = Math.abs(current.getTextComponent().getCaret().getMark()
						- current.getTextComponent().getCaret().getDot());
			}
		}

		length.setText((provider.getString("length") + ": " + len));
		statusBar.setText(provider.getString("ln") + ln + "  " + provider.getString("col") + col + "  "
				+ provider.getString("sel") + sel);
	}

	/**
	 * Method initializes actions
	 */
	private void configureActions() {
		openDocument = new OpenDocumentAction(provider, this);
		changeLanguageToCroatian = new ChangeLanguageCroAction(provider);
		changeLanguageToEnglish = new ChangeLanguageEnAction(provider);
		changeLanguageToGerman = new ChangeLanguageDeAction(provider);
		openNewDocument = new CreateEmptyDocumentAction(provider, this);
		saveDocumentAs = new SaveDocumentAsAction(provider, this);
		saveDocument = new SaveDocumentAction(provider, this, saveDocumentAs);
		close = new CloseDocumentAction(provider, this, saveDocument);
		exitApplication = new ExitAction(provider, this, close);
		setUpExit();
		cut = new LocalizableAction("cut", provider) {
			private static final long serialVersionUID = 1L;

			/**
			 * Action to perform
			 */
			Action cutA = new DefaultEditorKit.CutAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				cutA.actionPerformed(e);
			}
		};
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		copy = new LocalizableAction("copy", provider) {
			private static final long serialVersionUID = 1L;

			/**
			 * Action to perform
			 */
			Action copyA = new DefaultEditorKit.CopyAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				copyA.actionPerformed(e);
			}
		};
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		paste = new LocalizableAction("paste", provider) {
			private static final long serialVersionUID = 1L;

			/**
			 * Action to perform
			 */
			Action pasteA = new DefaultEditorKit.PasteAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				pasteA.actionPerformed(e);
			}
		};
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		info = new InfoAction(provider, this);
	}

	/**
	 * Creates menu bar
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new LocalizableJMenu("menu_file", provider);
		mb.add(file);
		file.add(openDocument);
		file.add(openNewDocument);
		file.addSeparator();
		file.add(saveDocument);
		file.add(saveDocumentAs);
		file.addSeparator();
		file.add(info);
		file.addSeparator();
		file.add(close);
		file.addSeparator();
		file.add(exitApplication);

		JMenu edit = new LocalizableJMenu("menu_edit", provider);
		edit.add(copy);
		edit.add(cut);
		edit.add(paste);
		mb.add(edit);

		JMenu languages = new LocalizableJMenu("menu_lan", provider);
		languages.add(changeLanguageToCroatian);
		languages.add(changeLanguageToEnglish);
		languages.add(changeLanguageToGerman);
		mb.add(languages);

		JMenu tools = new LocalizableJMenu("menu_tools", provider);
		JMenu caseM = new LocalizableJMenu("menu_case", provider);
		caseM.add(toUpperCase);
		caseM.add(toLowerCase);
		caseM.add(toggleSelectedPart);
		tools.add(caseM);
		JMenu sort = new LocalizableJMenu("menu_sort", provider);
		sort.add(sortAsc);
		sort.add(sortDes);
		tools.add(sort);
		tools.add(uniqueA);
		mb.add(tools);

		setJMenuBar(mb);
	}

	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(openDocument));
		tb.add(new JButton(openNewDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveDocumentAs));
		tb.add(new JButton(close));
		tb.add(new JButton(info));
		tb.add(new JButton(copy));
		tb.add(new JButton(cut));
		tb.add(new JButton(paste));
		tb.add(new JButton(exitApplication));

		getContentPane().add(tb, BorderLayout.PAGE_START);

		JToolBar status = new JToolBar();
		status.setFloatable(true);
		status.setLayout(new GridLayout(1, 3));
		length.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		status.add(length);
		statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		status.add(statusBar);
		clock.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		status.add(clock);
		status.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		getContentPane().add(status, BorderLayout.PAGE_END);
	}

	/**
	 * Action used for opening documents
	 */
	private Action openDocument;

	/**
	 * Changes lanugage to croatian
	 */
	private Action changeLanguageToCroatian;

	/**
	 * Changes lanugage to english
	 */
	private Action changeLanguageToEnglish;

	/**
	 * Changes lanugage to german
	 */
	private Action changeLanguageToGerman;

	/**
	 * Opens new document
	 */
	private Action openNewDocument;

	/**
	 * Saves document
	 */
	private Action saveDocument;

	/**
	 * Saves document as
	 */
	private Action saveDocumentAs;

	/**
	 * Closes current document
	 */
	private Action close;

	/**
	 * Cuts selected text
	 */
	private Action cut;

	/**
	 * Copies selected text
	 */
	private Action copy;

	/**
	 * Pastes text
	 */
	private Action paste;

	/**
	 * Action for informations
	 */
	private Action info;

	/**
	 * Updates clock
	 */
	private ActionListener updateClock;

	/**
	 * Inverses selected text
	 */
	private final Action toggleSelectedPart = new LocalizableAction("inverse", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();

			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			if (len == 0) {
				return;
			}
			try {
				String text = doc.getText(start, len);
				text = toggleText(text);
				doc.remove(start, len);
				doc.insertString(start, text, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Changes text to upper case
	 */
	private final Action toUpperCase = new LocalizableAction("toUpper", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();

			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			if (len == 0) {
				return;
			}
			try {
				String text = doc.getText(start, len);
				text = toUpper(text);
				doc.remove(start, len);
				doc.insertString(start, text, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Changes text to lower case
	 */
	private final Action toLowerCase = new LocalizableAction("toLower", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();

			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			if (len == 0) {
				return;
			}
			try {
				String text = doc.getText(start, len);
				text = toLower(text);
				doc.remove(start, len);
				doc.insertString(start, text, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Changes text to lower case
	 */
	private final Action sortAsc = new LocalizableAction("sortA", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Locale locale = new Locale(provider.getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			sortLines((o1, o2) -> collator.compare(o1, o2));
		}
	};

	/**
	 * Changes text to lower case
	 */
	private final Action sortDes = new LocalizableAction("sortD", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Locale locale = new Locale(provider.getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			sortLines((o1, o2) -> collator.compare(o2, o1));
		}
	};

	/**
	 * Removes duplicate lines
	 */
	private final Action uniqueA = new LocalizableAction("unique", provider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();

			int len = Math.abs(caret.getDot() - caret.getMark());

			if (len == 0) {
				return;
			}

			int min = Math.min(caret.getMark(), caret.getDot());
			int max = Math.max(caret.getMark(), caret.getDot());

			try {
				min = editor.getLineStartOffset(editor.getLineOfOffset(min));
				max = editor.getLineEndOffset(editor.getLineOfOffset(max));

				String lines = editor.getText(min, max - min);
				List<String> list = Arrays.asList(lines.split("\n"));
				List<String> uniqueList = new ArrayList<>();
				for (String line : list) {
					if (!uniqueList.contains(line)) {
						uniqueList.add(line);
					}
				}

				lines = String.join("\n", uniqueList);

				doc.remove(min, max - min);
				if (doc.getLength() - 1 > min) {
					lines += "\n";
				}
				doc.insertString(min, lines, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Sorts currently selected lines in directory
	 * 
	 * @param comp comparator
	 */
	private void sortLines(Comparator<String> comp) {
		JTextArea editor = JNotepadPP.this.getEditor().getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();

		int len = Math.abs(caret.getDot() - caret.getMark());

		if (len == 0) {
			return;
		}

		int min = Math.min(caret.getMark(), caret.getDot());
		int max = Math.max(caret.getMark(), caret.getDot());

		try {
			min = editor.getLineStartOffset(editor.getLineOfOffset(min));
			max = editor.getLineEndOffset(editor.getLineOfOffset(max));

			String lines = editor.getText(min, max - min);
			List<String> sortedLines = Arrays.asList(lines.split("\n"));
			sortedLines.sort(comp);
			lines = String.join("\n", sortedLines);

			doc.remove(min, max - min);
			if (doc.getLength() - 1 > min) {
				lines += "\n";
			}
			doc.insertString(min, lines, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Changes text from lower case to upper case and vice versa
	 * 
	 * @param text to be toggled
	 * @return toggled text
	 */
	private String toggleText(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			} else if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			}
		}
		return new String(chars);
	}

	/**
	 * Changes text from lower case to upper
	 * 
	 * @param text to be toggled
	 * @return toggled text
	 */
	private String toUpper(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			}
		}
		return new String(chars);
	}

	/**
	 * Changes text from upper case to lower case
	 * 
	 * @param text to be toggled
	 * @return toggled text
	 */
	private String toLower(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			}
		}
		return new String(chars);
	}

	/**
	 * Action used for termination of application
	 */
	private Action exitApplication;

	/**
	 * Sets up exit
	 */
	private void setUpExit() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplication.actionPerformed(new ActionEvent(JNotepadPP.this, 0, null));
				timer.stop();
			}
		});
	}

	/**
	 * Getter for editor
	 * 
	 * @return this editor
	 */
	public DefaultMultipleDocumentModel getEditor() {
		return editor;
	}

	/**
	 * Enables or disables actions in accordance with input
	 * 
	 * @param enable boolean
	 */
	private void enableActions(boolean enable) {
		toggleSelectedPart.setEnabled(enable);
		toLowerCase.setEnabled(enable);
		toUpperCase.setEnabled(enable);
		sortAsc.setEnabled(enable);
		sortDes.setEnabled(enable);
		uniqueA.setEnabled(enable);
	}

	/**
	 * Resolves title for current active document
	 * 
	 * @return title
	 */
	private String resolveTitle() {
		var doc = editor.getCurrentDocument();
		if (doc == null) {
			return TITLE;
		}

		if (doc.getFilePath() == null) {
			return provider.getString("new_file") + " - " + TITLE;
		}

		return doc.getFilePath() + " - " + TITLE;
	}

	/**
	 * Starts the program
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
