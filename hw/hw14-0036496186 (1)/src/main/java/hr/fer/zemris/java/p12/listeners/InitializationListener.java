package hr.fer.zemris.java.p12.listeners;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.model.Poll;

/**
 * Listener that sets up database on initialization
 * 
 * @author Matej
 *
 */
@WebListener
public class InitializationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties properties = new Properties();
		try {
			properties.load(Files
					.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"))));
		} catch (Exception e) {
			throw new IllegalStateException("Couldnt load properties file");
		}

		String connectionURL = "jdbc:derby://" + properties.getProperty("host") + ":" + properties.getProperty("port")
				+ "/" + properties.getProperty("name") + ";user=" + properties.getProperty("user") + ";password="
				+ properties.getProperty("password");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Couldn't initialise pool", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		try (Connection connection = cpds.getConnection();
				ResultSet results = connection.getMetaData().getTables(null, null, "%", null);) {
			List<String> list = new ArrayList<>();
			while (results.next()) {
				list.add(results.getString(3));
			}

			if (!list.contains("POLLS")) {
				createPolls(connection);
				fillTables(sce, connection, list);
			} else {
				if (isPollsEmpty(connection)) {
					fillTables(sce, connection, list);
				} else {
					if (!list.contains("POLLOPTIONS")) {
						deleteAllFromPolls(connection);
						fillTables(sce, connection, list);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
			// throw new RuntimeException("Couldn't initialize tables");
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Checks if table is empty and returns that information
	 * 
	 * @param connection to communicate with
	 * @return true if table is empty
	 * @throws SQLException if errors occurred
	 */
	private boolean isPollsEmpty(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM POLLS");
		ResultSet rs = ps.executeQuery();
		boolean notEmpty = rs.next();
		rs.close();
		return !notEmpty;
	}

	/**
	 * Puts values into tables
	 * 
	 * @param sce        servletContextEvent
	 * @param connection for communication
	 * @param list       of present tables
	 * @throws SQLException if error occurred
	 * @throws IOException  if error occurred
	 */
	private void fillTables(ServletContextEvent sce, Connection connection, List<String> list)
			throws SQLException, IOException {
		if (list.contains("POLLOPTIONS")) {
			deleteAllFromPollOptions(connection);
		} else {
			createPollsOptions(connection);
		}

		Path path = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/polls.txt"));
		List<String> pollsDefinition = Files.readAllLines(path);
		for (String line : pollsDefinition) {
			String[] arr = line.split("\t");// title message path
			Poll poll = insertIntoPolls(connection, arr);
			List<String> pollsOptions = Files
					.readAllLines(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/" + arr[2])));
			for (String pollsOp : pollsOptions) {
				String[] array = pollsOp.split("\t");
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");
				ps.setString(1, array[1]);
				ps.setString(2, array[2]);
				ps.setString(3, String.valueOf(poll.getId()));
				ps.setString(4, "0");
				ps.executeUpdate();
			}

		}
	}

	/**
	 * Inserts value into Polls and returns inserted poll
	 * 
	 * @param connection for communications
	 * @param arr        array with values
	 * @return generated poll
	 * @throws SQLException if errors occurred
	 */
	private Poll insertIntoPolls(Connection connection, String[] arr) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO POLLS (title, message) values (?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, arr[0]);
		ps.setString(2, arr[1]);
		ps.executeUpdate();
		ResultSet key = ps.getGeneratedKeys();
		key.next();
		Poll poll = new Poll(key.getInt(1), arr[0], arr[1]);
		key.close();
		return poll;
	}

	/**
	 * Deletes all entries from polls table
	 * 
	 * @param connection for communication
	 * @throws SQLException if errors occurred
	 */
	private void deleteAllFromPolls(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM POLLS WHERE 1=1");
		ps.executeUpdate();
	}

	/**
	 * Deletes all entries from pollOptions table
	 * 
	 * @param connection for communication
	 * @throws SQLException if errors occurred
	 */
	private void deleteAllFromPollOptions(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM POLLOPTIONS WHERE 1=1");
		ps.executeUpdate();
	}

	/**
	 * Creates polls table
	 * 
	 * @param connection for communication
	 * @throws SQLException if errors occurred
	 */
	private void createPollsOptions(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
						+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
						+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
						+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
		ps.executeUpdate();
	}

	/**
	 * Creates polls table
	 * 
	 * @param connection for communication
	 * @throws SQLException if errors occurred
	 */
	private void createPolls(Connection connection) throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement("CREATE TABLE Polls" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "title VARCHAR(150) NOT NULL," + "message CLOB(2048) NOT NULL" + ")");
		ps.executeUpdate();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
