package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.RegistrationForm;

/**
 * Worker used for authors
 * 
 * @author mfures
 *
 */
@WebServlet("/servleti/author/*")
public class Author extends HttpServlet {
	private static final long serialVersionUID = -2201926482034290L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser bu = (BlogUser) req.getSession().getAttribute("logedUser");
		String[] arr = req.getPathInfo().substring(1).split("/");

		BlogUser author = DAOProvider.getDAO().getUser(arr[0]);
		if (author == null) {
			req.setAttribute("message", "Invalid author");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("author", author);

		if (arr.length == 1) {
			List<BlogEntry> blogEntries = DAOProvider.getDAO().getAllEntries(author);
			req.setAttribute("blogEntries", blogEntries);
			req.setAttribute("editable", bu==null?false:bu.getId() == author.getId());
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
		}

		if (arr.length != 2 && arr.length != 3) {
			req.setAttribute("message", "Invalid path");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		handle(req, resp, bu, arr, author);
	}

	/**
	 * Handles request
	 * 
	 * @param req    request
	 * @param resp   response
	 * @param bu     BlogUser
	 * @param arr    array
	 * @param author author
	 * @throws ServletException if errors occurred
	 * @throws IOException      if errors occurred
	 */
	private void handle(HttpServletRequest req, HttpServletResponse resp, BlogUser bu, String[] arr, BlogUser author)
			throws ServletException, IOException {
		if (arr.length == 2) {
			if (arr[1].equals("new")) {
				// TODO
			} else if (arr[1].equals("edit")) {
				if (bu==null?true:bu.getId() != author.getId()) {
					req.setAttribute("Not allowed", "Invalid author");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				} else {
					// TODO
				}
			} else {
				req.setAttribute("message", "Invalid path");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		} else {
			if (arr[1].equals("edit")) {
				if (bu==null?true:bu.getId() != author.getId()) {
					req.setAttribute("Not allowed", "Invalid author");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				} else {
					
				}
			} else {
				req.setAttribute("message", "Invalid path");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO
	}
}
