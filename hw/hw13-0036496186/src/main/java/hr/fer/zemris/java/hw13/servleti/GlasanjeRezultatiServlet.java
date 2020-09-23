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

import hr.fer.zemris.java.hw13.servleti.GlasanjeServlet.Entry;

/**
 * Worker in charge of reading entries from file on disc
 * 
 * @author Matej
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 27580471606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		if (!Files.exists(path)) {
			Files.createFile(path);
		}

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

		for (var mapEntry : map.entrySet()) {
			Result tmp = new Result(mapEntry.getValue(), 0, mapEntry.getKey());
			if (!results.contains(tmp)) {
				results.add(tmp);
			}
		}

		results.sort((x, y) -> y.getVotes().compareTo(x.getVotes()));
		req.setAttribute("results", results);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Defines an result of pool
	 * 
	 * @author Matej
	 *
	 */
	public static class Result {
		/**
		 * Entry band
		 */
		private Entry entry;

		/**
		 * Num of votes
		 */
		private Integer votes;

		/**
		 * Id of entry
		 */
		private int id;

		/**
		 * Constructor
		 * 
		 * @param entry to be set
		 * @param votes to be set
		 */
		public Result(Entry entry, int votes, int id) {
			this.entry = entry;
			this.votes = votes;
			this.id = id;
		}

		/**
		 * Getter for entry
		 * 
		 * @return entry
		 */
		public Entry getEntry() {
			return entry;
		}

		/**
		 * Getter for votes
		 * 
		 * @return votes
		 */
		public Integer getVotes() {
			return votes;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Result other = (Result) obj;
			if (id != other.id)
				return false;
			return true;
		}
	}
}
