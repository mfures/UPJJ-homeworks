package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Searches database for user with given nick, and returns said user if it's
	 * found
	 * 
	 * @param nick of user
	 * @return user or null
	 * @throws DAOException if errors occurred
	 */
	public BlogUser getUser(String nick) throws DAOException;

	/**
	 * Persists BlogUser
	 * 
	 * @param bu BLogUser to persist
	 * @throws DAOException if errors occurred
	 */
	public void persistBlogUser(BlogUser bu) throws DAOException;

	/**
	 * Retrieves list of all currently registered BlogUsers
	 * 
	 * @return list of all registered BlogUsers
	 * @throws DAOException if errors occurred
	 */
	public List<BlogUser> getAllUsers() throws DAOException;

	/**
	 * Retrieves list of all entries for some user
	 * 
	 * @param bu BlogUser
	 * @return list of all registered BlogUsers
	 * @throws DAOException if errors occurred
	 */
	public List<BlogEntry> getAllEntries(BlogUser bu) throws DAOException;
}