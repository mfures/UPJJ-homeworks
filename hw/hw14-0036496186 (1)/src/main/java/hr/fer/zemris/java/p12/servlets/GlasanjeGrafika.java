package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Worker in charge of making pie chart image
 * 
 * @author Matej
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
	private static final long serialVersionUID = 275804710606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(req.getParameter("pollID"));
		} catch (Exception e) {
			resp.sendRedirect("index.html");
			return;
		}

		Poll poll = DAOProvider.getDao().getPoll(id);
		if (poll == null) {
			resp.sendRedirect("index.html");
			return;
		}

		List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(id);

		JFreeChart jfc = createChart(createDataset(pollOptions), "OS usage");
		resp.setContentType("image/png");
		resp.getOutputStream().write(ChartUtilities.encodeAsPNG(jfc.createBufferedImage(400, 400)));
	}

	/**
	 * Creates data set
	 * 
	 * @param results
	 * 
	 * @return data set
	 */
	private PieDataset createDataset(List<PollOptions> results) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (int i = 0; i < results.size(); i++) {
			result.setValue(results.get(i).getOptionTitle(), results.get(i).getVotesCount());
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
