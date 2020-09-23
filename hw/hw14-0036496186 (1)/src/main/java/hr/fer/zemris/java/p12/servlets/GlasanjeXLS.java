package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Worker in charge of creating an xml file
 * 
 * @author Matej
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {
	private static final long serialVersionUID = 2758097106656684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(req.getParameter("pollID"));
		} catch (Exception e) {
			resp.sendRedirect("index.html");
			return;
		}

		Poll poll = DAOProvider.getDao().getPoll(id);
		if (poll == null) {
			resp.sendRedirect("index.html");
			return;
		}

		List<PollOptions> results = DAOProvider.getDao().getPollOptions(id);
		results.sort((x, y) -> Integer.valueOf(y.getVotesCount()).compareTo(Integer.valueOf(x.getVotesCount())));

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("Rezultati");
		for (int i = 0; i < results.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(results.get(i).getOptionTitle());
			row.createCell(1).setCellValue(results.get(i).getVotesCount());
		}

		wb.write(resp.getOutputStream());
		wb.close();

	}
}
