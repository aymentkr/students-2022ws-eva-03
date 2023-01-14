package whz.pti.eva.services.user.dto;

/**
 * The Class CustomerDTO.
 */
public class CustomerDTO {
	

	/** The id. */
	private long id;

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The login name. */
	private String loginName;

	/** The email. */
	private String email;

	/** The activated state of the user. */
	private boolean active;
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the login name.
	 *
	 * @param loginName the new login name
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the activated state.
	 *
	 * @return true if active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the activated state.
	 *
	 * @param active the new state
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
