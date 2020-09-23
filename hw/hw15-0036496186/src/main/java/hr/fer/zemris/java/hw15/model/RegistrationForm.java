package hr.fer.zemris.java.hw15.model;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Form used on registration page
 * 
 * @author mfures
 *
 */
public class RegistrationForm {
	/**
	 * Argument firstName provided in form
	 */
	private String firstName;

	/**
	 * Argument firstName provided in form
	 */
	private String lastName;

	/**
	 * Argument firstName provided in form
	 */
	private String email;

	/**
	 * Argument firstName provided in form
	 */
	private String nick;

	/**
	 * Argument firstName provided in form
	 */
	private String password;

	/**
	 * Map used for storing errors
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param firstName to be set
	 * @param lastName  to be set
	 * @param email     to be set
	 * @param nick      to be set
	 * @param password  to be set
	 */
	public RegistrationForm(String firstName, String lastName, String email, String nick, String password) {
		this.firstName = inputOrEmpty(firstName);
		this.lastName = inputOrEmpty(lastName);
		this.email = inputOrEmpty(email);
		this.nick = inputOrEmpty(nick);
		this.password = inputOrEmpty(password);
	}

	/**
	 * If given input is null, returns empty string, otherwise returns input
	 * 
	 * @param input to be checked
	 * @return input or empty string
	 */
	private String inputOrEmpty(String input) {
		if (input == null) {
			return "";
		}

		return input;
	}

	/**
	 * Getter for firstName
	 * 
	 * @return value of firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return value of lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for email
	 * 
	 * @return value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter for nick
	 * 
	 * @return value of nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Getter for password
	 * 
	 * @return value of password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for errors
	 * 
	 * @return value of errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Setter for firstName
	 * 
	 * @param firstName to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = inputOrEmpty(firstName);
	}

	/**
	 * Setter for lastName
	 * 
	 * @param lastName to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = inputOrEmpty(lastName);
	}

	/**
	 * Setter for email
	 * 
	 * @param email to be set
	 */
	public void setEmail(String email) {
		this.email = inputOrEmpty(email);
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick to be set
	 */
	public void setNick(String nick) {
		this.nick = inputOrEmpty(nick);
	}

	/**
	 * Setter for password
	 * 
	 * @param password to be set
	 */
	public void setPassword(String password) {
		this.password = inputOrEmpty(password);
	}

	/**
	 * Returns true if there were no errors in Form
	 * 
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		return errors.isEmpty();
	}

	/**
	 * Validates form
	 */
	public void validate() {
		errors.clear();
		validate(firstName, 35, "firstName", "First name");
		validate(lastName, 35, "lastName", "Last name");
		validate(nick, 20, "nick", "Nick");
		validate(password, 100, "password", "Password");
		validate(email, 100, "email", "Email");
		if (!errors.containsKey("email")) {
			if (email.startsWith("@") || email.endsWith("@") || !email.contains("@")) {
				errors.put("email", "Invalid email format");
			}
		}

	}

	/**
	 * Validates some input
	 * 
	 * @param input  to be validated
	 * @param maxLen maximal length of input
	 * @param nameK  key to be put in errors map
	 * @param name   Variable used for writing
	 */
	private void validate(String input, int maxLen, String nameK, String name) {
		if (input.isEmpty()) {
			errors.put(nameK, name + " is empty");
		} else if (input.length() > maxLen) {
			errors.put(nameK, name + " is to long");
		}
	}

	/**
	 * Turns password to passwordHash using SHA-1 algorithm
	 * 
	 * @return paswordHash version of password
	 */
	public String toPasswordHash() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (Exception ignored) {
		}

		byte[] bytes = password.getBytes();
		md.update(bytes, 0, bytes.length);

		bytes = md.digest();

		StringBuilder sb = new StringBuilder();

		for (Byte byt : bytes) {
			sb.append(String.format("%02x", byt));
		}

		return sb.toString();
	}

	/**
	 * Construct s BlogUser out of given form
	 * 
	 * @return corresponding BlogUser to this form. If form is invalid, null
	 */
	public BlogUser createUser() {
		validate();
		if (errors.isEmpty()) {
			BlogUser bu = new BlogUser();
			bu.setFirstName(firstName);
			bu.setLastName(lastName);
			bu.setEmail(email);
			bu.setNick(nick);
			bu.setPasswordHash(toPasswordHash());
			return bu;
		} else {
			return null;
		}
	}
}
