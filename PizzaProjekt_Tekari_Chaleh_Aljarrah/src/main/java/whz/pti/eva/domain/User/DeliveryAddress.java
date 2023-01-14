package whz.pti.eva.domain.user;

import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

/**
 * The Class DeliveryAddress.
 */
@Entity
public class DeliveryAddress extends BaseEntity<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5458013947010174888L;
	
	/** The street. */
	private String street;
	
	/** The housenumber. */
	private String housenumber;
	
	/** The town. */
	private String town;
	
	/** The postal code. */
	private String postalCode;
	
	/** The customers. */
	@ManyToMany(mappedBy = "deliveryAddresses")
	private List<Customer> customers;


	/**
	 * Instantiates a new delivery address.
	 */
	public DeliveryAddress(String street, String housenumber, String town, String postalCode, List<Customer> customers) {
		this.street = street;
		this.housenumber = housenumber;
		this.town = town;
		this.postalCode = postalCode;
		this.customers = customers;
	}

	/**
	 * Instantiates a new delivery address.
	 */
	public DeliveryAddress() {
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Gets the housenumber.
	 *
	 * @return the housenumber
	 */
	public String getHousenumber() {
		return housenumber;
	}
	
	/**
	 * Sets the housenumber.
	 *
	 * @param housenumber the new housenumber
	 */
	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}
	
	/**
	 * Gets the town.
	 *
	 * @return the town
	 */
	public String getTown() {
		return town;
	}
	
	/**
	 * Sets the town.
	 *
	 * @param town the new town
	 */
	public void setTown(String town) {
		this.town = town;
	}
	
	/**
	 * Gets the postal code.
	 *
	 * @return the postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * Sets the postal code.
	 *
	 * @param postalCode the new postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * Gets the customers.
	 *
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}
	
	/**
	 * Sets the customers.
	 *
	 * @param customers the new customers
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
