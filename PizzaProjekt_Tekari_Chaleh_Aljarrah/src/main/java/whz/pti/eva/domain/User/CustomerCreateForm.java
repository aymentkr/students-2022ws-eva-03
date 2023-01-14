package whz.pti.eva.domain.user;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * The Class CustomerCreateForm.
 */
public class CustomerCreateForm {
 
	/** The first name. */
	@NotEmpty
	private String firstName;
	
	/** The last name. */
	@NotEmpty
	private String lastName;
	
	/** The login name. */
	@NotEmpty
	private String loginName;
	
	/** The email. */
	@NotEmpty
	private String email;
	
	/** The Password hash. */
	@NotEmpty
	private String password;
	
	/** The Password hash repeated. */
	@NotEmpty
	private String passwordRepeated;
	
	/** The delivery addresses. */
	private List<DeliveryAddress> deliveryAddresses;


	public CustomerCreateForm() {}
	
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the password repeated.
	 *
	 * @return the password repeated
	 */
	public String getPasswordRepeated() {
		return this.passwordRepeated;
	}

	/**
	 * Sets the password repeated.
	 *
	 * @param passwordRepeated the new password repeated
	 */
	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
	/**
	 * Gets the delivery addresses.
	 *
	 * @return the delivery addresses
	 */
	public List<DeliveryAddress> getDeliveryAddresses() {
		return deliveryAddresses;
	}
	
	/**
	 * Sets the delivery addresses.
	 *
	 * @param deliveryAddresses the new delivery addresses
	 */
	public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}
}
