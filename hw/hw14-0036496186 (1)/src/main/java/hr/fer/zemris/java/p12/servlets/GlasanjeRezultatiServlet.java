package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Worker in charge of reading entries from file on disc
 * 
 * @author Matej
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 27580471606684633L;

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
		
		pollOptions.sort((x,y)->Integer.valueOf(y.getVotesCount()).compareTo(Integer.valueOf(x.getVotesCount())));

		
		req.setAttribute("results", pollOptions);
		req.setAttribute("poll", poll);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
