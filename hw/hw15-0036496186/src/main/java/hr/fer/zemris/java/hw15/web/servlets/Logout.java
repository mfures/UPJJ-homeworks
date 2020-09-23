package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker used for logout
 * 
 * @author mfures
 *
 */
@WebServlet("/servleti/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = -29282034831290L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();

		resp.sendRedirect("main");
	}
}