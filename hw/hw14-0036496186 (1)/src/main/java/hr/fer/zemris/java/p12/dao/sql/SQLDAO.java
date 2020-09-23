package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getAllPolls() {
		Connection connection = SQLConnectionProvider.getConnection();
		List<Poll> polls = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM POLLS");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Poll poll = new Poll(rs.getInt(1), rs.getString(2), rs.getString(3));
				polls.add(poll);
			}
		} catch (SQLException ignored) {
		}

		return polls;
	}

	@Override
	public Poll getPoll(int id) {
		Poll poll = null;
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM POLLS WHERE id=" + String.valueOf(id));
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				poll = new Poll(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException ignored) {
		}

		return poll;
	}

	@Override
	public List<PollOptions> getPollOptions(int id) {
		List<PollOptions> pollOptions = new ArrayList<>();
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM POLLOPTIONS WHERE POLLID=" + String.valueOf(id));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PollOptions po = new PollOptions(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5));
				pollOptions.add(po);
			}
		} catch (SQLException ignored) {
		}
		return pollOptions;
	}

	@Override
	public PollOptions getPollOption(int id) {
		PollOptions pollOption = null;
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM POLLOPTIONS WHERE ID=" + String.valueOf(id));
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pollOption = new PollOptions(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5));
			}
		} catch (SQLException ignored) {
		}
		return pollOption;
	}

	@Override
	public void setVotes(int id, int votes) {
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE POLLOPTIONS SET VOTESCOUNT=" + String.valueOf(votes) + " WHERE ID=" + String.valueOf(id));
			ps.executeUpdate();
		} catch (SQLException ignored) {
		}
	}

}