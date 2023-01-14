package whz.pti.eva.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.CustomerCreateForm;
import whz.pti.eva.services.user.customer.CustomerService;
import whz.pti.eva.services.user.dto.CustomerDTO;
import whz.pti.eva.services.user.validator.CustomerCreateFormValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Class CustomerController.
 */
@Controller
public class CustomerController {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	/** The customer service. */
	private final CustomerService customerService;
	
	/** The customer create form validator. */
	private final CustomerCreateFormValidator customerCreateFormValidator;
	
	/**
	 * Instantiates a new customer controller.
	 *
	 * @param customerService the customer service
	 * @param customerCreateFormValidator the customer create form validator
	 */
	@Autowired
	public CustomerController(CustomerService customerService,
			CustomerCreateFormValidator customerCreateFormValidator) {
		this.customerService = customerService;
		this.customerCreateFormValidator = customerCreateFormValidator;
	}

	/**
	 * Bind the validators.
	 *
	 * @param dataBinder the databinder object
	 */
	@InitBinder("userCreateForm")
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(this.customerCreateFormValidator);
	}

	/**
	 * Returns an HTML view with all the registered customers.
	 *
	 * @param model the view model
	 * @return html view
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(path = "/customers")
	public String showAllCustomers(Model model) {
		
		List<CustomerDTO> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		
		return "Admin";
	}

	/**
	 * Updates the state of the oone customer
	 *
	 * @param userId the given user id
	 * @param state the desired state
	 * @param model the view model
	 * @return html view
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(path = "/customer/setState")
	public String setStateOfCustomer(@RequestParam("userId") String userId, @RequestParam("state") boolean state, Model model) {

		customerService.setActivatedStateOfCustomerWithUserId(userId, state);

		return "redirect:/customers";
	}

	/**
	 * Returns an HTML view with the current customer profile.
	 *
	 * @param model the view model
	 * @return html view
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/my_profile")
	public String showUserProfile(Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		List<Customer> customers = new ArrayList<>();
		Optional<Customer> customer = customerService.getCustomerByLoginName(userId);
		customer.ifPresent(customer1 -> customers.add(customer1));

		model.addAttribute("customers", customers);

		return "Profile";
	}

	/**
	 * Returns an HTML view with a register form.
	 *
	 * @param model the view model
	 * @return html view
	 */
	@GetMapping(path = "/register")
	public String getRegisterPage(Model model) {
		
		model.addAttribute("userCreateForm", new CustomerCreateForm());
		return "UserForm";
	}

	/**
	 * Confirms the filled out register form.
	 *
	 * @param customerCreateForm the filled out form
	 * @param bindingResult the result of the validation
	 * @param model the view model
	 * @return html view
	 */
	@PostMapping(path = "/register/confirm")
	public String register(@Valid @ModelAttribute("userCreateForm") CustomerCreateForm customerCreateForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userCreateForm", new CustomerCreateForm());
			for (ObjectError error : bindingResult.getAllErrors()) {
				switch (error.getCode()) {
					case "nicknameError":
						model.addAttribute("nicknameError", error.getDefaultMessage());
						break;
					case "emailError":
						model.addAttribute("emailError", error.getDefaultMessage());
						break;
					case "passwordError":
						model.addAttribute("passwordError", error.getDefaultMessage());
					default:
						// ignore codes which are not displayed in the view
						break;
				}
			}
			return "UserForm";
		}
		else {
			Customer newCustomer = customerService.create(customerCreateForm);
			customerService.addNewCustomer(newCustomer);
			return "redirect:/login";
		}
	}
}
