package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker in charge of calculating trigonometric values
 * 
 * @author Matej
 */
@WebServlet("/trigonometric")
public class TrigonometricWorker extends HttpServlet {
	private static final long serialVersionUID = 2758097106656684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = parseOrDefault(req.getParameter("a"), 0);
		int b = parseOrDefault(req.getParameter("b"), 360);
		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		double[] sinValues = new double[b - a + 1], cosValues = new double[b - a + 1];
		
		for(int i=a;i<=b;i++) {
			sinValues[i-a]=Math.sin(Math.toRadians(i));
			cosValues[i-a]=Math.cos(Math.toRadians(i));
		}
		
		req.setAttribute("sinValues", sinValues);
		req.setAttribute("cosValues", cosValues);
		req.setAttribute("a", a);

		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Tries to parse string to integer, if it fails, returns default value given
	 * 
	 * @param aString  to be parsed
	 * @param defaultV will be returned if can't parse as int
	 * @return parsed string to int or defualt
	 */
	private int parseOrDefault(String aString, int defaultV) {
		try {
			defaultV = Integer.parseInt(aString);
		} catch (Exception e) {
		}

		return defaultV;
	}

}
