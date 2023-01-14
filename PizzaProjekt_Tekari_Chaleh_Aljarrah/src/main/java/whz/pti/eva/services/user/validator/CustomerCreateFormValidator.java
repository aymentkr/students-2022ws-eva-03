package whz.pti.eva.services.user.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import whz.pti.eva.domain.user.CustomerCreateForm;
import whz.pti.eva.services.user.customer.CustomerService;

/**
 * The Class CustomerCreateFormValidator.
 */
@Component
public class CustomerCreateFormValidator implements Validator {
    
    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(CustomerCreateFormValidator.class);
    
    /** The customer service. */
    private final CustomerService customerService;
	
	/**
	 * Instantiates a new customer create form validator.
	 *
	 * @param customerService the customer service
	 */
	@Autowired
    public CustomerCreateFormValidator(CustomerService customerService) {
		this.customerService = customerService;
	}
    
	/**
	 * Supports.
	 *
	 * @param clazz the clazz
	 * @return true, if successful
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CustomerCreateForm.class);
	}
	

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        log.debug("Validating {}", target);
        CustomerCreateForm form = (CustomerCreateForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
        validateLoginName(errors, form);
    }

    /**
     * Validate passwords.
     *
     * @param errors the errors
     * @param form the form
     */
    private void validatePasswords(Errors errors, CustomerCreateForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("passwordError", "unterschiedliche passwoerter eingegeben! vertippt?");
        }
    }

    /**
     * Validate email.
     *
     * @param errors the errors
     * @param form the form
     */
    private void validateEmail(Errors errors, CustomerCreateForm form) {
        if (customerService.existsByEmail(form.getEmail())) {
            errors.reject("emailError", "nutzer mit dieser email existiert bereits !!!");
        }
    }

    /**
     * Validate the login name.
     *
     * @param errors the errors
     * @param form the form
     */
    private void validateLoginName(Errors errors, CustomerCreateForm form) {
        if (customerService.existsByLoginName(form.getLoginName())) {
            errors.reject("nicknameError", "der login name ist bereits vergeben !!!");
        }
    }
}
