package whz.pti.eva.domain.user;

import org.springframework.security.core.authority.AuthorityUtils;

/**
 * The Class CurrentCustomer.
 */
public class CurrentCustomer extends org.springframework.security.core.userdetails.User {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8436736898128842988L;
	
	/** The customer. */
	private Customer customer;

	/**
	 * Instantiates a new current customer.
	 *
	 * @param customer the customer
	 */
	public CurrentCustomer(Customer customer) {
		super(
				customer.getLoginName(),
				customer.getPasswordHash(),
				customer.isActive(),
				true,
				true,
				true,
				AuthorityUtils.createAuthorityList(customer.getRole().toString()));
		this.customer = customer;
	}
	
	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return customer.getId();
	}
	
	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public String getLoginName() {
		return customer.getLoginName();
		
	}

	/**
	 * Gets the string representation of the CurrentCustomer object.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "CurrentCustomer [customer=" + customer + "]";
	}

}
