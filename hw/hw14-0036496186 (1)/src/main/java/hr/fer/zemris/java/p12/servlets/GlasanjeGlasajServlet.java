package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Worker in charge of reading entries from file on disc
 * 
 * @author Matej
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 2758047106606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (Exception e) {
			resp.sendRedirect("index.html");
			return;
		}
		PollOptions pollOption;

		synchronized (GlasanjeGlasajServlet.class) {
			pollOption = DAOProvider.getDao().getPollOption(id);
			if (pollOption == null) {
				resp.sendRedirect("index.html");
				return;
			}

			DAOProvider.getDao().setVotes(pollOption.getId(), pollOption.getVotesCount() + 1);
		}
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollOption.getPollID());
	}
}
