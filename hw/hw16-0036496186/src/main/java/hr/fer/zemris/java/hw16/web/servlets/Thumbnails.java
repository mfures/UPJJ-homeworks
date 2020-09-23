package hr.fer.zemris.java.hw16.web.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker that delivers thumbnails
 * 
 * @author mfures
 *
 */
@WebServlet(urlPatterns = "/thmb")
public class Thumbnails extends HttpServlet {
	private static final long serialVersionUID = -16463087138296724L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		if (name == null) {
			resp.sendError(500, "Bad request");
			return;
		}

		Path pathT = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"));
		synchronized (this) {
			if (!Files.isDirectory(pathT)) {
				Files.createDirectories(pathT);
			}
		}


		String newName = name + ".png";
		Path newThumb = pathT.resolve(newName);
		synchronized (Thumbnails.class) {
			if (Files.exists(newThumb)) {
				Path thumbPath = Paths.get("/WEB-INF/thumbnails").resolve(newName);
				try (InputStream is = req.getServletContext().getResourceAsStream(thumbPath.toString())) {
					ImageIO.write(ImageIO.read(is), "png", resp.getOutputStream());
				}
			} else {
				Path pathP = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike")).resolve(name);
				if (!Files.exists(pathP)) {
					resp.sendError(404, "Not found");
					return;
				}

				Path path = Paths.get("/WEB-INF/slike").resolve(name);
				try (InputStream is = req.getServletContext().getResourceAsStream(path.toString())) {
					BufferedImage original = ImageIO.read(is);
					BufferedImage small = new BufferedImage(150, 150, original.getType());

					Graphics2D g2d = small.createGraphics();
					g2d.drawImage(original, 0, 0, 150, 150, null);
					g2d.dispose();

					ImageIO.write(small, "png", Files.newOutputStream(newThumb));
					ImageIO.write(small, "png", resp.getOutputStream());
				}
			}

		}
	}
}
