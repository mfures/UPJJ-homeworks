package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	/**
	 * Gets all polls
	 * 
	 * @return list of polls
	 */
	List<Poll> getAllPolls();

	/**
	 * Returns poll with given id
	 * 
	 * @param id of poll
	 * @return poll, or null if id doesn't exist
	 */
	Poll getPoll(int id);

	/**
	 * Retrieves poll options for given id
	 * 
	 * @param id for poll
	 * @return list of pollOptions
	 */
	List<PollOptions> getPollOptions(int id);

	/**
	 * Retrieves poll option for given pollOptions id
	 * 
	 * @param id for pollOptions
	 * @return pollOptions
	 */
	PollOptions getPollOption(int id);

	/**
	 * Sets votes count
	 * 
	 * @param id    of pollOptions
	 * @param votes votes
	 */
	void setVotes(int id, int votes);
}