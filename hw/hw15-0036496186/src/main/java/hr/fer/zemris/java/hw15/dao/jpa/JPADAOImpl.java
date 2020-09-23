package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getUser(String nick) throws DAOException {
		try {
			return (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("SELECT bu FROM BlogUser AS bu WHERE bu.nick=:x").setParameter("x", nick)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void persistBlogUser(BlogUser bu) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(bu);
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().createQuery("SELECT bu FROM BlogUser AS bu").getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getAllEntries(BlogUser bu) throws DAOException {
		return JPAEMProvider.getEntityManager().createQuery("SELECT be FROM BlogEntry as be where be.creator=:x")
				.setParameter("x", bu).getResultList();
	}

}