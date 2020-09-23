package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.servleti.GlasanjeRezultatiServlet.Result;
import hr.fer.zemris.java.hw13.servleti.GlasanjeServlet.Entry;

/**
 * Worker in charge of creating an xml file
 * 
 * @author Matej
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {
	private static final long serialVersionUID = 2758097106656684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Result> results = resolveResults(req);
		if (results == null) {
			return;
		}
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("Rezultati");
		for (int i = 0; i < results.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(results.get(i).getEntry().getName());
			row.createCell(1).setCellValue(results.get(i).getVotes());
		}

		wb.write(resp.getOutputStream());
		wb.close();

	}

	/**
	 * Resolves results
	 * 
	 * @param req request
	 * @return results
	 * @throws IOException
	 */
	private List<Result> resolveResults(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path path = Paths.get(fileName);
		List<String> lines2 = Files.readAllLines(path);
		Map<Integer, Entry> map = new HashedMap<>();
		lines2.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				String[] values = t.split("\t");
				map.put(Integer.parseInt(values[0]), new Entry(values[1], values[2]));
			}
		});

		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		path = Paths.get(fileName);
		if (Files.exists(path)) {
			List<Result> results = new ArrayList<>();
			List<String> lines = Files.readAllLines(path);

			lines.forEach(new Consumer<String>() {
				@Override
				public void accept(String t) {
					String[] values = t.split(" ");
					results.add(new Result(map.get(Integer.parseInt(values[0])), Integer.parseInt(values[1]),
							Integer.parseInt(values[0])));
				}
			});

			for (var mapEntry : map.entrySet()) {
				Result tmp = new Result(mapEntry.getValue(), 0, mapEntry.getKey());
				if (!results.contains(tmp)) {
					results.add(tmp);
				}
			}

			results.sort((x, y) -> y.getVotes().compareTo(x.getVotes()));
			return results;
		} else {
			return null;
		}
	}
}
