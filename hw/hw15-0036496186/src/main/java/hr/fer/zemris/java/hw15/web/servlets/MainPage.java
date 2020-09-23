package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

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
@WebServlet("/servleti/main")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = -21926482034831290L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationForm form = new RegistrationForm(null, null, null, null, null);
		req.setAttribute("form", form);
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		RegistrationForm form = new RegistrationForm(null, null, null, req.getParameter("nick"),
				req.getParameter("password"));
		req.setAttribute("form", form);

		BlogUser taken = DAOProvider.getDAO().getUser(form.getNick());
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);

		if (taken != null) {
			if (form.toPasswordHash().equals(taken.getPasswordHash())) {
				req.getSession().setAttribute("logedUser", taken);
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
				return;
			} else {
				form.getErrors().put("nick", "");
			}
		} else {
			form.getErrors().put("nick", "");
		}

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}
