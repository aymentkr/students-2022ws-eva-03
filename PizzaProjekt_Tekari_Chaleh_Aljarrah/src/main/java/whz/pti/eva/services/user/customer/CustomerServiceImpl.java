package whz.pti.eva.services.user.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.user.*;
import whz.pti.eva.services.user.dto.CustomerDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The Class CustomerServiceImpl.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	/** The customer repository. */
	private final CustomerRepository customerRepository;

	/**
	 * Instantiates a new customer service implements.
	 *
	 * @param customerRepository the customer repository
	 */
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	@Override
	public List<CustomerDTO> getAllCustomers() {
		log.debug("Getting all Customers");
		List<Customer> targetListOrigin = customerRepository.findAll();
		List<CustomerDTO> targetList = new ArrayList<>();
		for (Customer source : targetListOrigin) {
			CustomerDTO target = new CustomerDTO();
			BeanUtils.copyProperties(source, target);
			targetList.add(target);
		}
		return targetList;
	}

	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 */
	@Override
	public CustomerDTO getCustomerById(long id) {
		log.debug("Getting Customers={}", id);
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format(">>> Customer=%s not found", id)));
		CustomerDTO userDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, userDTO);
		return userDTO;
	}

	/**
	 * Gets the customer by email.
	 *
	 * @param email the email
	 * @return the customer by email
	 */
	@Override
	public Optional<Customer> getCustomerByEmail(String email) {
		log.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
		return customerRepository.findCustomerByEmail(email);
	}
	
	/**
	 * Gets the customer by login name.
	 *
	 * @param loginName the login name
	 * @return the customer by login name
	 */
	@Override
	public Optional<Customer> getCustomerByLoginName(String loginName) {
		log.debug("Getting user by loginName={}", loginName);
		return customerRepository.findByLoginName(loginName);
	}

	/**
	 * Exists by login name.
	 *
	 * @param loginName the login name
	 * @return true, if successful
	 */
	@Override
	public boolean existsByLoginName(String loginName) {
		return customerRepository.existsByLoginName(loginName);
	}

	/**
	 * Exists by email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	@Override
	public boolean existsByEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

	/**
	 * Adds the new customer.
	 *
	 * @param customer the customer
	 */
	@Override
	public void addNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	/**
	 * Creates new Customer.
	 *
	 * @param form the form
	 * @return the customer
	 */
	@Override
	public Customer create(CustomerCreateForm form) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Customer newCustomer = new Customer();
		newCustomer.setEmail(form.getEmail());
		newCustomer.setFirstName(form.getFirstName());
		newCustomer.setLastName(form.getLastName());
		newCustomer.setLoginName(form.getLoginName());
		newCustomer.setPasswordHash(encoder.encode(form.getPassword()));
		newCustomer.setDeliveryAddresses(form.getDeliveryAddresses());
		newCustomer.setRole(Role.USER);
		newCustomer.setActive(true);
		return newCustomer;
	}

	/**
	 * Sets the activated state for the customer with a given userId.
	 *
	 * @param userId the new state of the customer
	 * @param state the new state of the customer
	 */
	@Override
	@Transactional
	public void setActivatedStateOfCustomerWithUserId(String userId, boolean state) {
		Optional<Customer> customer = customerRepository.findByLoginName(userId);
		customer.ifPresent((customer1 -> customer1.setActive(state)));
	}

	/**
	 * Adds the delivery address to customer with id.
	 *
	 * @param id the id
	 * @param address the address
	 */
	@Override
	public void addDeliveryAddressToCustomerWithId(long id, DeliveryAddress address) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(String.format(">>> Customer=%s not found", id)));
		List<DeliveryAddress> deliveryAddresses = new ArrayList<>();
		deliveryAddresses.add(address);
		customer.setDeliveryAddresses(deliveryAddresses);
	}

}
