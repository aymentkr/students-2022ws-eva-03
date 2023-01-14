package whz.pti.eva.domain.user;

import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * The Class Customer.
 */
@Entity
public class Customer extends BaseEntity<Long> {


	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The login name. */
	private String loginName;
	
	/** The email. */
	private String email; 
	

	/** The password hash. */
	private String passwordHash;
	
	/** The role. */
	@Enumerated(EnumType.STRING)
	private Role role;
	
	/** The delivery addresses. */
	@ManyToMany
	private List<DeliveryAddress> deliveryAddresses;
	
	/** The active. */
	private boolean active;

	/**
	 * Instantiates a new Customer.
	 *
	 * @param firstName the first name of the customer
	 * @param lastName the las name of the customer
	 * @param loginName the login name of the customer
	 * @param email the email of the customer
	 * @param passwordHash the password hash of the customer
	 * @param role the role of the customer
	 * @param deliveryAddresses a list of delivery addresses
	 * @param active the active state of the customer
	 */
	public Customer(String firstName, String lastName, String loginName, String email, String passwordHash, Role role, List<DeliveryAddress> deliveryAddresses, boolean active) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginName = loginName;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
		this.deliveryAddresses = deliveryAddresses;
		this.active = active;
	}

	/**
	 * Instantiates a new Customer.
	 */
    public Customer() {

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
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * Sets the role.
	 * 
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.role = role;
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
	


	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
