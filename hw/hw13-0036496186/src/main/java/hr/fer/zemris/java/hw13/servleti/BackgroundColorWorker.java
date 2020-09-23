package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker in charge of changing background color
 * 
 * @author Matej
 */
@WebServlet("/setcolor")
public class BackgroundColorWorker extends HttpServlet {
	private static final long serialVersionUID = 2758097106606684633L;

	/**
	 * List of supported colors
	 */
	private List<String> supportedColors;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color=req.getParameter("bgColor");
		if(supportedColors.contains(color)) {
			req.getSession().setAttribute("pickedBgCol", color);
		}
		
		req.getRequestDispatcher("colors.jsp").forward(req,resp);
	}
	
	@Override
	public void init() throws ServletException {
		super.init();

		supportedColors=new ArrayList<>();
		supportedColors.add("FFFFFF");//white
		supportedColors.add("FF0000");//red
		supportedColors.add("00FF00");//green
		supportedColors.add("00FFFF");//cyan
	}

}
