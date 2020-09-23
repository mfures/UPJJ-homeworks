package hr.fer.zemris.java.p12.model;

/**
 * Defines polls options and all relevant informations about said poll
 * 
 * @author Matej
 *
 */
public class PollOptions {
	/**
	 * Polls id
	 */
	private int id;

	/**
	 * Polls option title
	 */
	private String optionTitle;

	/**
	 * Polls option link
	 */
	private String optionLink;

	/**
	 * Polls id
	 */
	private int pollID;

	/**
	 * Polls votes count
	 */
	private int votesCount;

	/**
	 * Creates poll options
	 * 
	 * @param id          id of poll options
	 * @param optionTitle option title
	 * @param optionLink  option link
	 * @param pollID      originall polls id
	 * @param votesCount  number of votes
	 */
	public PollOptions(int id, String optionTitle, String optionLink, int pollID, int votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Getter for id
	 * 
	 * @return pollsOptions id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for optionTitle
	 * 
	 * @return pollsOptions optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Getter for optionLink
	 * 
	 * @return pollsOptions optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Getter for pollID
	 * 
	 * @return pollsOptions pollID
	 */
	public int getPollID() {
		return pollID;
	}

	/**
	 * Getter for votesCount
	 * 
	 * @return pollsOptions votesCount
	 */
	public int getVotesCount() {
		return votesCount;
	}
}
