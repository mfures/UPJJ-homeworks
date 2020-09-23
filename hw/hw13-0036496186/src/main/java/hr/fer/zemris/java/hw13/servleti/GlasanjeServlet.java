package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker in charge of reading entries from file on disc
 * 
 * @author Matej
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 2758047106606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<Integer, Entry> map = new TreeMap<>();

		lines.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				String[] values = t.split("\t");
				map.put(Integer.parseInt(values[0]), new Entry(values[1], values[2]));
			}
		});
				
		req.setAttribute("entries", map);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Entry for map
	 * 
	 * @author Matej
	 *
	 */
	public static class Entry {
		/**
		 * Name of band
		 */
		private String name;

		/**
		 * Link to video
		 */
		private String link;

		/**
		 * Constructor
		 * 
		 * @param name
		 * @param link
		 */
		public Entry(String name, String link) {
			this.name = name;
			this.link = link;
		}

		/**
		 * Getter for name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for link
		 * 
		 * @return link
		 */
		public String getLink() {
			return link;
		}
	}
}
