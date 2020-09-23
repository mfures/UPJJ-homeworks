package hr.fer.zemris.java.hw15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Defines a blog user
 * 
 * @author Matej
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {
	/**
	 * Users id
	 */
	private Long id;

	/**
	 * Users first name
	 */
	private String firstName;

	/**
	 * Users last name
	 */
	private String lastName;

	/**
	 * Users nick
	 */
	private String nick;

	/**
	 * Users email
	 */
	private String email;

	/**
	 * Users passwordHas
	 */
	private String passwordHash;

	/**
	 * List of users blogEntries
	 */
	private List<BlogEntry> blogEntries;

	/**
	 * Getter for Id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for firstName
	 * 
	 * @return firstName
	 */
    @Column(length = 35, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName
	 * 
	 * @param firstName to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return lastName
	 */
    @Column(length = 35, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName
	 * 
	 * @param lastName to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
    @Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick to be set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
    @Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email to be set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for passwordHash
	 * 
	 * @return passwordHash
	 */
    @Column(length = 100, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for passwordHash
	 * 
	 * @param passwordHash to be set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for blogEntries
	 * 
	 * @return blogEntries
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Setter for blogEntries
	 * 
	 * @param blogEntries to be set
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
