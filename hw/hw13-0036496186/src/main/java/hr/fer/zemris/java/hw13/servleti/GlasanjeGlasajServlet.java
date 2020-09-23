package hr.fer.zemris.java.hw13.servleti;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Worker in charge of reading entries from file on disc
 * 
 * @author Matej
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 2758047106606684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object idO = req.getParameter("id");
		if (idO == null) {
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
			return;
		}

		int id;
		try {
			id = Integer.parseInt((String) idO);
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
			return;
		}

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Integer> list = new ArrayList<>();

		lines.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				String[] values = t.split("\t");
				list.add(Integer.parseInt(values[0]));
			}
		});

		if (!list.contains(id)) {
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
			return;
		}

		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);
		synchronized (GlasanjeGlasajServlet.class) {
			if (Files.exists(path)) {
				lines = Files.readAllLines(path);
				boolean newEntry=true;
				for(int i=0;i<lines.size();i++) {
					if (lines.get(i).startsWith(String.valueOf(id))) {
						String[] arr = lines.get(i).split("\\s");
						arr[1] = String.valueOf(Integer.parseInt(arr[1]) + 1);
						newEntry=false;
						lines.set(i, arr[0]+" "+arr[1]);
						break;
					}
				}
				
				if(newEntry) {
					lines.add(id + " 1");
				}

				try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(path))) {
					String input=String.join("\n", lines);
					os.write(input.getBytes(StandardCharsets.UTF_8));
					os.flush();
				} catch (Exception e) {
				}
			} else {
				Files.createFile(path);
				Files.write(path, (new String(id + " 1")).getBytes(StandardCharsets.UTF_8));
			}
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
