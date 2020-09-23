package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Worker in charge of creating an xml file
 * 
 * @author Matej
 */
@WebServlet("/powers")
public class PowersWorker extends HttpServlet {
	private static final long serialVersionUID = 2758097106656684633L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int a = Integer.parseInt(req.getParameter("a"));
			int b = Integer.parseInt(req.getParameter("b"));
			int n = Integer.parseInt(req.getParameter("n"));

			if (validInput(a, b, n)) {
				resp.setContentType("application/vnd.ms-excel");
				resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

				HSSFWorkbook wb = new HSSFWorkbook();

				int m = n;
				while (m != 0) {
					HSSFSheet sheet = wb.createSheet(Integer.toString(n - m + 1));
					for (int i = 0; i < Math.abs(a - b)+1; i++) {
						HSSFRow row = sheet.createRow(i);
						row.createCell(0).setCellValue(a + i);
						row.createCell(1).setCellValue(Math.pow(a + i, n - m + 1));
					}
					m--;
				}

				wb.write(resp.getOutputStream());
				wb.close();
			} else {
				req.getRequestDispatcher("WEB-INF/pages/invalidEntry.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			req.getRequestDispatcher("WEB-INF/pages/invalidEntry.jsp").forward(req, resp);
		}
	}

	/**
	 * Checks if arguments are valid
	 * 
	 * @param a first number
	 * @param b second number
	 * @param n power
	 * @return true if parameters are valid
	 */
	private boolean validInput(int a, int b, int n) {
		if (a >= -100 && b >= -100 && n >= 1) {
			if (a <= 100 && b <= 100 && n <= 5) {
				if (a < b) {
					return true;
				}
			}
		}

		return false;
	}

}
