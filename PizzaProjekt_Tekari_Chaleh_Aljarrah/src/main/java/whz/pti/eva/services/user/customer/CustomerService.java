package whz.pti.eva.services.user.customer;

import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.CustomerCreateForm;
import whz.pti.eva.domain.user.DeliveryAddress;
import whz.pti.eva.services.user.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;
/**
 * The Interface CustomerService.
 */
public interface CustomerService {

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	List<CustomerDTO> getAllCustomers();
	

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 */
	CustomerDTO getCustomerById(long id);
	
	/**
	 * Gets the customer by email.
	 *
	 * @param email the email
	 * @return the customer by email
	 */
	Optional<Customer> getCustomerByEmail(String email);
	
	/**
	 * Gets a customer by its login name.
	 *
	 * @param loginName the requested login name
	 * @return the corresponding Customer
	 */
	Optional<Customer> getCustomerByLoginName(String loginName);
    
    /**
     * Exists by login name.
     *
     * @param loginNamee the login namee
     * @return true, if successful
     */
    boolean existsByLoginName(String loginNamee);
    
    /**
     * Exists by email.
     *
     * @param email the email
     * @return true, if successful
     */
    boolean existsByEmail(String email);
	
	/**
	 * Adds the new customer.
	 *
	 * @param customer the customer
	 */
	void addNewCustomer(Customer customer);
	
	/**
	 * Sets the activated state for the customer with a given userId.
	 *
	 * @param userId the new state of the customer
	 */
	void setActivatedStateOfCustomerWithUserId(String userId, boolean state);
	
	/**
	 * Adds the delivery address to customer with id.
	 *
	 * @param id the id
	 * @param address the address
	 */
	void addDeliveryAddressToCustomerWithId(long id, DeliveryAddress address);
	
	
	/**
	 * Creates the Customer.
	 *
	 * @param form the form
	 * @return the customer
	 */
	Customer create(CustomerCreateForm form);
}
