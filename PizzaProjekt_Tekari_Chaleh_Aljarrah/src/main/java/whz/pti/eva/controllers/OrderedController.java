package whz.pti.eva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import whz.pti.eva.domain.ordered.Ordered;
import whz.pti.eva.services.ordered.OrderedService;
import whz.pti.eva.domain.user.CurrentCustomer;

import java.util.List;

/**
 * The Class OrderedController.
 */
@Controller
public class OrderedController {

	/** The ordered service. */
	private OrderedService orderedService;
	
	/**
	 * Instantiates a new ordered controller.
	 *
	 * @param orderedService the ordered service
	 */
	@Autowired
	public OrderedController(OrderedService orderedService) {
		this.orderedService = orderedService;
	}
	
	/**
	 * Gets all the orders of the currently logged-in user.
	 *
	 * @param model the view model
	 *
	 * @return a view with all the orders
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/my_orders")
	public String getAllOrdersFromCurrentUser(Model model) {
		String userId = ((CurrentCustomer) model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		List<Ordered> orders = orderedService.orderListByUserId(userId);
		model.addAttribute("orders", orders);

		return "Orders";
	}
}
