package whz.pti.eva.controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import whz.pti.eva.MPA.PaypalService;
import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.services.cart.CartService;
import whz.pti.eva.services.pizza.PizzaService;
import whz.pti.eva.services.validator.NewItemFormValidator;

import javax.validation.Valid;

/**
 * The Class CartController.
 */
@Controller
public class CartController {
	/**
	 * The Service.
	 */
	@Autowired
	PaypalService service;

	/**
	 * The constant SUCCESS_URL.
	 */
	public static final String SUCCESS_URL = "pay/success";
	/**
	 * The constant CANCEL_URL.
	 */
	public static final String CANCEL_URL = "pay/cancel";


	/** The cart service. */
	private CartService cartService;

	/** The pizza service. */
	private PizzaService pizzaService;

	/** The new item form Validator. */
	private NewItemFormValidator newItemFormValidator;


	/**
	 * Instantiates a new cart controller.
	 *
	 * @param cartService          the cart service
	 * @param pizzaService         the pizza service
	 * @param newItemFormValidator the new item form validator
	 */
	@Autowired
	public CartController(CartService cartService, PizzaService pizzaService, NewItemFormValidator newItemFormValidator) {
		this.cartService = cartService;
		this.pizzaService = pizzaService;
		this.newItemFormValidator = newItemFormValidator;
	}

	/**
	 * Inits the binder.
	 *
	 * @param dataBinder the data binder
	 */
	@InitBinder("newItemForm")
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(this.newItemFormValidator);
	}

	/**
	 * Returns an html view of the contents of a given user.
	 *
	 * @param userId the user id of the user
	 * @param model  the model object
	 * @return Warenkorb html view
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(path = "/cart/{userId}")
	public String currentShoppingCart(@PathVariable("userId") String userId, Model model) {
		Cart cart = cartService.findByUserIdOrElseCreateAndReturn(userId);
		model.addAttribute("cart", cart);
		model.addAttribute("sum", cartService.calculateSumOfAllItemsInCartWithUserId(userId));
		
		return "Warenkorb";
	}

	/**
	 * Returns an html view of the contents of the cart of the current user.
	 *
	 * @param model the model object
	 * @return Warenkorb html view
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/cart")
	public String currentShoppingCart(Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		Cart cart = cartService.findByUserIdOrElseCreateAndReturn(userId);
		model.addAttribute("cart", cart);
		model.addAttribute("sum", cartService.calculateSumOfAllItemsInCartWithUserId(userId));
		
		return "Warenkorb";
	}

	/**
	 * Adds the item to cart.
	 *
	 * @param newItemForm   the new item form
	 * @param bindingResult the binding result
	 * @param model         the model
	 * @return the string
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/addItemToCart")
	public String addItemToCart(@Valid @ModelAttribute("newItemForm") NewItemForm newItemForm, BindingResult bindingResult, Model model) {
		model.addAttribute("pizzas", pizzaService.pizzaList());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", bindingResult.getGlobalError().getDefaultMessage());
			return "PizzaForever-App";
		}
		
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		cartService.addItemToCartWithUserId(userId, newItemForm);
		model.addAttribute("message", "Ihre Bestellung wurde hinzugefuegt, Sie koennen weitere hinzufuegen.");
		
		return "PizzaForever-App";
	}

	/**
	 * Removes item from the cart.
	 *
	 * @param itemId Id of the item
	 * @param model  the model object
	 * @return redirects to cart
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/removeItemFromCart")
	public String removeItemFromCart(@RequestParam("itemId") long itemId , Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		cartService.removeItemFromCartWithUserItem(userId, itemId);

		return "redirect:/cart";
	}

	/**
	 * Updates size of pizza.
	 *
	 * @param newPizzaSize updated size of Pizza
	 * @param itemId       Id of the item
	 * @param model        the model object
	 * @return redirects to cart
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/updatePizzaSize")
	public String updatePizzaSize (@RequestParam("size") PizzaSize newPizzaSize, @RequestParam("itemId") long itemId , Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		cartService.updatePizzaSizeFromItemInCart(userId, itemId,  newPizzaSize);

		return "redirect:/cart";
	}

	/**
	 * Updates quantity of Pizza.
	 *
	 * @param newQuantity updated quantity of Pizza
	 * @param itemId      Id of the item
	 * @param model       the model object
	 * @return redirects to cart
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/updatePizzaQuantity")
	public String updatePizzaQuantity(@RequestParam("quantity") int newQuantity, @RequestParam("itemId") long itemId, Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		cartService.updateQuantityFromItemInCart(userId, itemId, newQuantity);

		return "redirect:/cart";
	}


	/**
	 * Process cart to order.
	 *
	 * @param from  the from
	 * @param model the model
	 * @return the string
	 */
	@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/pay", method = {RequestMethod.POST})
	public String processCartToOrder(@RequestParam String from, Model model) {
		String userId = ((CurrentCustomer)model.asMap().get("currentCustomer")).getCustomer().getLoginName();
		Double total = cartService.calculateSumOfAllItemsInCartWithUserId(userId).doubleValue();;
		cartService.processCartWithUserIdToOrder(from);
		Cart cart = cartService.findByUserIdOrElseCreateAndReturn(from);

		try {
			Payment payment = service.createPayment(total,"EUR", "paypal",
					"sale", "Payment Gateway With @Paypal", "http://localhost:9090/" + CANCEL_URL,
					"http://localhost:9090/" + SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}


        cartService.savePayment(cart, String.valueOf(total), from);
		cartService.deleteItemsInCartWithUserId(from);
		return "redirect:/";
		//return "Done";
	}


	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	

}
