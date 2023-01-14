package whz.pti.eva.services.user.currentCustomer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.services.user.customer.CustomerService;

/**
 * The Class CurrentCustomerDetailsService.
 */
@Service
public class CurrentCustomerDetailsService implements UserDetailsService  {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CurrentCustomerDetailsService.class);
	
	/** The customer service. */
	private CustomerService customerService;
	
    /**
     * Instantiates a new current customer details service.
     *
     * @param customerService the customer service
     */
    @Autowired
	public CurrentCustomerDetailsService(CustomerService customerService) {
		this.customerService = customerService;
	}
    
    /**
     * Load Customer by username.
     *
     * @param loginName the login name
     * @return the current customer
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public CurrentCustomer loadUserByUsername(String loginName) throws UsernameNotFoundException {
    	log.debug("Authenticating user with loginName={}", loginName);
    	log.info("try to get user: " + loginName);
    	Customer customer = customerService.getCustomerByLoginName(loginName).orElseThrow(() ->
		new UsernameNotFoundException("User with loginName= " + loginName + " cannot be not found"));
    	return new CurrentCustomer(customer);
    }
	
	
}
