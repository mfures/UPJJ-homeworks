package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Worker in charge of making pie chart image
 * 
 * @author Matej
 */
@WebServlet("/reportImage")
public class ReportImageWorker extends HttpServlet {
	private static final long serialVersionUID = 2758047106606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JFreeChart jfc = createChart(createDataset(), "OS usage");
		resp.setContentType("image/png");
		resp.getOutputStream().write(ChartUtilities.encodeAsPNG(jfc.createBufferedImage(400, 400)));
	}

	/**
	 * Creates dataset
	 * 
	 * @return dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 69);
		result.setValue("Mac", 55);
		result.setValue("Windows", 37);
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
