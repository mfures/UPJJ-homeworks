package hr.fer.zemris.java.hw16.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker that delivers images
 * 
 * @author mfures
 *
 */
@WebServlet(urlPatterns = "/image")
public class ImagesServlet extends HttpServlet {
	private static final long serialVersionUID = -1645563087138296724L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(500, "Bad request");
			return;
		}

		Path path = Paths.get("/WEB-INF/slike").resolve(name);

		try (InputStream is = req.getServletContext().getResourceAsStream(path.toString())) {
			if (is != null) {
				BufferedImage bi = ImageIO.read(is);
				ImageIO.write(bi, "png", resp.getOutputStream());
			} else {
				resp.sendError(404, "Not found");
			}
		}
	}
}
