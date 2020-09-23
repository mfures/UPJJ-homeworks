package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.collor.label.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.collor.util.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.drawing.Tool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.drawing.geometrical.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.DrawingObjectsListModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.model.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawing.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.drawing.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.drawing.visitors.GeometricalObjectPainter;

/**
 * Simple paint application
 * 
 * @author mfures
 *
 */
public class JVDraw extends JFrame {
	private static final long serialVersionUID = 5966362432584232509L;

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;

	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;

	/**
	 * List model
	 */
	private DrawingObjectsListModel list;

	/**
	 * Circle tool
	 */
	private Tool circleTool;

	/**
	 * Line tool
	 */
	private Tool lineTool;

	/**
	 * FilledCircle tool
	 */
	private Tool filledCircleTool;

	/**
	 * Currently used tool
	 */
	private Tool currentTool;

	/**
	 * Foreground color
	 */
	private JColorArea fgColorArea;

	/**
	 * Background color
	 */
	private JColorArea bgColorArea;

	/**
	 * Used for opening
	 */
	private Action open;

	/**
	 * Used for saving
	 */
	private Action save;

	/**
	 * Used for saving as
	 */
	private Action saveAs;

	/**
	 * Used for saving as
	 */
	private Action exit;

	/**
	 * Used for saving as
	 */
	private Action export;

	/**
	 * Path for saving
	 */
	private Path path;

	/**
	 * Constructor initializes the window
	 */
	public JVDraw() {
		setSize(1000, 600);
		setTitle("JVDraw");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit.actionPerformed(null);
			}
		});

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		drawingModel = new DrawingModelImpl();
		canvas = new JDrawingCanvas(drawingModel, new ToolSupplier());
		list = new DrawingObjectsListModel(drawingModel);

		cp.add(canvas, BorderLayout.CENTER);

		initProviders();
		initTools();

		JToolBar toolbar = new JToolBar();
		toolbar.add(fgColorArea);
		toolbar.addSeparator();
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		ButtonGroup bg = new ButtonGroup();
		initButtons(bg, toolbar);

		toolbar.setVisible(true);
		cp.add(toolbar, BorderLayout.NORTH);
		cp.add(new JColorLabel(fgColorArea, bgColorArea), BorderLayout.SOUTH);

		JList<GeometricalObject> jlist = new JList<GeometricalObject>(list);
		initList(jlist);
		cp.add(new JScrollPane(jlist), BorderLayout.EAST);

		initActions();
		initMenu();
	}

	/**
	 * Initializes menu
	 */
	private void initMenu() {
		JMenu file = new JMenu("File");
		file.add(open);
		file.addSeparator();
		file.add(export);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(exit);

		JMenuBar menubar = new JMenuBar();
		menubar.add(file);

		setJMenuBar(menubar);
	}

	/**
	 * Initializes actions
	 */
	private void initActions() {
		save = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8423451021877667503L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (path == null) {
					saveAs.actionPerformed(e);
					return;
				}

				StringBuilder sb = new StringBuilder();
				GeometricalObject go;
				for (int i = 0; i < drawingModel.getSize(); i++) {
					go = drawingModel.getObject(i);

					if (go instanceof Circle) {
						sb.append(((Circle) go).write());
					} else if (go instanceof Line) {
						sb.append(((Line) go).write());
					} else {
						sb.append(((FilledCircle) go).write());
					}
				}

				try {
					Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(JVDraw.this, "Couldn't save");
					return;
				}

				drawingModel.clearModifiedFlag();
			}
		};

		save.putValue(Action.NAME, "Save");
		save.putValue(Action.SHORT_DESCRIPTION, "Save file to disc");

		saveAs = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3715382565443345795L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("JVDraw files", "jvd");
				jfc.setFileFilter(fnef);

				if (jfc.showSaveDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
					Path selected = jfc.getSelectedFile().toPath();
					if (!selected.toString().endsWith(".jvd")) {
						JOptionPane.showConfirmDialog(JVDraw.this, "Invalid extension");
						return;
					}

					if (Files.exists(selected)) {
						if (JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to overwrite?", "Overwrite",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
							return;
						}
					}

					path = selected;
					setTitle("JVDraw " + selected);
					save.actionPerformed(e);
				}
			}
		};

		saveAs.putValue(Action.NAME, "Save As");
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Save As file to disc");

		exit = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -83481833466420513L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (drawingModel.isModified()) {
					int code = JOptionPane.showConfirmDialog(JVDraw.this, "There are unsaved changes. Want to save?",
							"Exiting", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (code == JOptionPane.YES_OPTION) {
						if (path == null) {
							save.actionPerformed(e);
						} else {
							saveAs.actionPerformed(e);
						}
					} else if (code == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				JVDraw.this.dispose();
			}
		};

		exit.putValue(Action.NAME, "Exit");
		exit.putValue(Action.SHORT_DESCRIPTION, "Exit application");

		open = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5646512628170369692L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (drawingModel.isModified()) {
					int code = JOptionPane.showConfirmDialog(JVDraw.this, "There are unsaved changes. Want to save?",
							"Exiting", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (code == JOptionPane.YES_OPTION) {
						if (path == null) {
							save.actionPerformed(e);
						} else {
							saveAs.actionPerformed(e);
						}
					} else if (code == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}

				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("JVDraw files", "jvd");
				jfc.setFileFilter(fnef);
				if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION)
					return;

				Path selected = jfc.getSelectedFile().toPath();

				try {
					List<String> lines = Files.readAllLines(selected);
					List<GeometricalObject> tmp = new ArrayList<>();

					for (String line : lines) {
						if (line.startsWith("LINE")) {
							tmp.add(Line.parse(line));
						} else if (line.startsWith("CIRCLE")) {
							tmp.add(Circle.parseLine(line));
						} else if (line.startsWith("FCIRCLE")) {
							tmp.add(FilledCircle.parseLine(line));
						} else {
							throw new RuntimeException();
						}
					}

					drawingModel.clear();

					for (GeometricalObject go : tmp) {
						drawingModel.add(go);
					}

					path = selected;
					setTitle("JVDraw " + selected);
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(JVDraw.this, "Invalid extension");
				}
			}
		};

		open.putValue(Action.NAME, "Open");
		open.putValue(Action.SHORT_DESCRIPTION, "Open some picture");

		export = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7262011589990823939L;

			@Override
			public void actionPerformed(ActionEvent e) {
				GeometricalObjectBBCalculator calc = new GeometricalObjectBBCalculator();
				for (int i = 0; i < drawingModel.getSize(); i++) {
					drawingModel.getObject(i).accept(calc);
				}

				Rectangle box = calc.getBoundingBox();
				BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g2d = image.createGraphics();
				g2d.translate(-box.x, -box.y);
				g2d.setColor(Color.WHITE);
				g2d.fillRect(box.x, box.y, box.width, box.height);
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
				for (int i = 0, size = drawingModel.getSize(); i < size; i++) {
					drawingModel.getObject(i).accept(painter);
				}
				g2d.dispose();

				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("Extension", "jpg", "png", "gif");
				jfc.setFileFilter(fnef);
				jfc.setDialogTitle("Export");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Didn't save", "No save", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Path selected = jfc.getSelectedFile().toPath();
				if (!selected.toString().endsWith(".jpg") && !selected.toString().endsWith(".gif")
						&& !selected.toString().endsWith(".png")) {
					JOptionPane.showConfirmDialog(JVDraw.this, "Invalid extension");
					return;
				}

				if (Files.exists(selected)) {
					if (JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to overwrite?", "Overwrite",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
						return;
					}
				}

				try {
					ImageIO.write(image, selected.toString().substring(selected.toString().length() - 3),
							jfc.getSelectedFile());
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(JVDraw.this, "Couldn't save");
				}
			}
		};

		export.putValue(Action.NAME, "Export");
		export.putValue(Action.SHORT_DESCRIPTION, "Export picture");
	}

	/**
	 * INitializes list
	 * 
	 * @param list2 to initialize
	 */
	private void initList(JList<GeometricalObject> list) {
		list.setVisible(true);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2)
					return;
				int index = list.getSelectedIndex();
				if (index == -1)
					return;

				GeometricalObjectEditor editor = drawingModel.getObject(index).createGeometricalObjectEditor();
				if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit object",
						JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
					}
				}
			}
		});
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				GeometricalObject selected = list.getSelectedValue();
				if (selected == null)
					return;

				int code = e.getKeyCode();
				if (code == KeyEvent.VK_DELETE) {
					drawingModel.remove(selected);
				}
				if (code == KeyEvent.VK_PLUS || code == KeyEvent.VK_ADD) {
					drawingModel.changeOrder(selected, -1);
				}
				if (code == KeyEvent.VK_MINUS || code == KeyEvent.VK_SUBTRACT) {
					drawingModel.changeOrder(selected, 1);
				}

			}
		});
	}

	/**
	 * Initializes all buttons
	 * 
	 * @param bg      button group
	 * @param toolbar toolBar
	 */
	private void initButtons(ButtonGroup bg, JToolBar toolbar) {
		initButton("Line    ", lineTool, bg, toolbar);
		initButton("Circle    ", circleTool, bg, toolbar);
		initButton("Filled circle     ", filledCircleTool, bg, toolbar);

	}

	private void initButton(String string, Tool tool, ButtonGroup bg, JToolBar toolbar) {
		JToggleButton button = new JToggleButton(string);
		button.addActionListener(l -> {
			currentTool = tool;
		});

		bg.add(button);
		toolbar.add(button);
	}

	/**
	 * Initializes providers
	 */
	private void initProviders() {
		fgColorArea = new JColorArea(Color.RED);
		bgColorArea = new JColorArea(Color.BLUE);
	}

	/**
	 * Initializes tools
	 */
	private void initTools() {
		lineTool = new LineTool(fgColorArea, canvas, drawingModel);
		circleTool = new CircleTool(fgColorArea, canvas, drawingModel);
		filledCircleTool = new FilledCircleTool(fgColorArea, bgColorArea, canvas, drawingModel);
		currentTool = lineTool;
	}

	/**
	 * Starts the program
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}

	/**
	 * Supplier for current tool
	 * 
	 * @author mfures
	 *
	 */
	private class ToolSupplier implements Supplier<Tool> {
		@Override
		public Tool get() {
			return currentTool;
		}
	}
}
