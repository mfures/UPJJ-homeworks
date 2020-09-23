package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Demo example for working with BarChart
 * 
 * @author matfures
 *
 */
public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * BarChart that will be drawn
	 */
	private BarChart barchart;
	
	private Path path;

	/**
	 * Constructor. Creaters picture
	 * 
	 * @param barchart to be drawn
	 */
	private BarChartDemo(BarChart barchart,Path path) {
		this.barchart = barchart;
		this.path=path;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Initializes GUI
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JLabel(path.toString()),BorderLayout.PAGE_START);
		getContentPane().add(new BarChartComponent(barchart), BorderLayout.CENTER);
	}

	/**
	 * Starts the program
	 * 
	 * @param args path to file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Program needs 1 argument; path to file");
			System.exit(1);
		}

		Path p = null;
		try {
			p = Paths.get(args[0]);
		} catch (Exception e) {
			System.out.println("Program needs 1 argument; path to file");
			System.exit(1);
		}

		if (!Files.exists(p)) {
			System.out.println("Program needs 1 argument; path to file.");
			System.exit(1);
		}
		
		if (Files.isDirectory(p)) {
			System.out.println("Program needs 1 argument; path to file. You gave directory");
			System.exit(1);
		}

		try {
			BarChart b = handleLinesFromInput(p);
			Path p2=p;
			SwingUtilities.invokeLater(() -> new BarChartDemo(b,p2).setVisible(true));
		} catch (Exception e) {
			System.out.println("Couldn't make a barchart");
			System.exit(1);
		}

	}

	/**
	 * Creates BarChart from input file. If it can't throws an exception
	 * 
	 * @param p to read from
	 * @return BarChart
	 * @throws IllegalArgumentException if anything goes wrong
	 */
	private static BarChart handleLinesFromInput(Path p) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(p.toString())))) {
			String line = br.readLine();

			Objects.requireNonNull(line);
			String xAxisName = line;
			line = br.readLine();

			Objects.requireNonNull(line);
			String yAxisName = line;
			line = br.readLine();

			Objects.requireNonNull(line);
			String values = line;
			List<XYValue> list = toList(values);
			line = br.readLine();

			Objects.requireNonNull(line);
			String yMinS = line;
			int yMin = Integer.parseInt(yMinS);
			line = br.readLine();

			Objects.requireNonNull(line);
			String yMaxS = line;
			int yMax = Integer.parseInt(yMaxS);
			line = br.readLine();

			Objects.requireNonNull(line);
			String yDifS = line;
			int yDif = Integer.parseInt(yDifS);

			return new BarChart(list, xAxisName, yAxisName, yMin, yMax, yDif);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Parses values to list of XYValues
	 * 
	 * @param s to be parsed
	 * @return list of values
	 * @throws IllegalArgumentException for invalid input
	 * @throws NumberFormatException    if inputs values can't be parsed to integers
	 */
	private static List<XYValue> toList(String s) {
		String[] arr = s.split("\\s+");
		List<XYValue> list = new ArrayList<>();

		for (String value : arr) {
			list.add(toXYValue(value));
		}

		return list;
	}

	/**
	 * Creates XYValue if it is possible. Otherwise throws an exception
	 * 
	 * @param v to be parsed
	 * @return XYVvalue parsed from string
	 * @throws IllegalArgumentException for invalid input
	 * @throws NumberFormatException    if inputs values can't be parsed to integers
	 */
	private static XYValue toXYValue(String v) {
		String[] arr = v.split(",");
		if (arr.length != 2) {
			throw new IllegalArgumentException("To many arguments");
		}

		return new XYValue(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
	}
}
