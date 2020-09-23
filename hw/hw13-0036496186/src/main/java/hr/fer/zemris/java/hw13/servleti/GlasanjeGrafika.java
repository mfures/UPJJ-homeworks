package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.hw13.servleti.GlasanjeRezultatiServlet.Result;
import hr.fer.zemris.java.hw13.servleti.GlasanjeServlet.Entry;

/**
 * Worker in charge of making pie chart image
 * 
 * @author Matej
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
	private static final long serialVersionUID = 275804710606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Result> results = resolveResults(req);
		if (results == null) {
			return;
		}

		JFreeChart jfc = createChart(createDataset(results), "OS usage");
		resp.setContentType("image/png");
		resp.getOutputStream().write(ChartUtilities.encodeAsPNG(jfc.createBufferedImage(400, 400)));
	}

	/**
	 * Resolves results
	 * 
	 * @param req request
	 * @return results
	 * @throws IOException
	 */
	private List<Result> resolveResults(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path path = Paths.get(fileName);
		List<String> lines2 = Files.readAllLines(path);
		Map<Integer, Entry> map = new HashedMap<>();
		lines2.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				String[] values = t.split("\t");
				map.put(Integer.parseInt(values[0]), new Entry(values[1], values[2]));
			}
		});

		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		path = Paths.get(fileName);
		if (Files.exists(path)) {
			List<Result> results = new ArrayList<>();
			List<String> lines = Files.readAllLines(path);

			lines.forEach(new Consumer<String>() {
				@Override
				public void accept(String t) {
					String[] values = t.split(" ");
					results.add(new Result(map.get(Integer.parseInt(values[0])), Integer.parseInt(values[1]),
							Integer.parseInt(values[0])));
				}
			});
			return results;
		} else {
			return null;
		}
	}

	/**
	 * Creates data set
	 * 
	 * @param results
	 * 
	 * @return data set
	 */
	private PieDataset createDataset(List<Result> results) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (int i = 0; i < results.size(); i++) {
			result.setValue(results.get(i).getEntry().getName(), results.get(i).getVotes());
		}

		if (results.size() == 0) {
			result.setValue("/", 1);
		}
		return result;

	}

	/**
	 * Cretes JFreeChart
	 * 
	 * @param dataset for chart
	 * @param title   charts title
	 * @return chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}

}
