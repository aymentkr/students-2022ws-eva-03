package whz.pti.eva.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import whz.pti.eva.domain.user.CurrentCustomer;

/**
 * The Class CurrentCustomerControllerAdvice.
 */
@ControllerAdvice
public class CurrentCustomerControllerAdvice {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentCustomerControllerAdvice.class);

    /**
     * Gets the current customer.
     *
     * @param authentication the authentication object
     *
     * @return the current customer
     */
    @ModelAttribute("currentCustomer")
    public CurrentCustomer getCurrentCustomer(Authentication authentication) {
        LOGGER.debug("authentication = " + authentication);
        return (authentication == null) ? null : (CurrentCustomer) authentication.getPrincipal();
    }

}
