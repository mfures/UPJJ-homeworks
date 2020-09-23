package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.RegistrationForm;

/**
 * Worker used for registration
 * 
 * @author mfures
 *
 */
@WebServlet("/servleti/register")
public class RegistrationPage extends HttpServlet {
	private static final long serialVersionUID = -2201926482034831290L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationForm form = new RegistrationForm(null, null, null, null, null);
		req.setAttribute("form", form);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		RegistrationForm form = new RegistrationForm(req.getParameter("firstName"), req.getParameter("lastName"),
				req.getParameter("email"), req.getParameter("nick"), req.getParameter("password"));
		form.validate();
		req.setAttribute("form", form);

		if (!form.isValid()) {
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		BlogUser bu = form.createUser();
		BlogUser taken = DAOProvider.getDAO().getUser(bu.getNick());

		if (taken != null) {
			form.getErrors().put("nick", "Nick already taken.");
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		DAOProvider.getDAO().persistBlogUser(bu);

		resp.sendRedirect(req.getContextPath() + "/");
	}
}
