package hr.fer.zemris.java.p12.model;

/**
 * Defines a single poll
 * 
 * @author Matej
 *
 */
public class Poll {
	/**
	 * Unique identifier
	 */
	private int id;

	/**
	 * Pools title
	 */
	private String title;

	/**
	 * Polls message
	 */
	private String message;

	/**
	 * Creates a poll
	 * 
	 * @param id      polls id
	 * @param title   polls title
	 * @param message polls message
	 */
	public Poll(int id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Getter for id of poll
	 * 
	 * @return polls id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for title of poll
	 * 
	 * @return polls title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for message of poll
	 * 
	 * @return polls message
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poll other = (Poll) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
