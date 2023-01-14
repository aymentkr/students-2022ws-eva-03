package whz.pti.eva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.pay.PayActionResponse;
import whz.pti.eva.pay.SmmpService;
import whz.pti.eva.services.cart.CartService;
import whz.pti.eva.services.validator.NewItemFormValidator;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.services.pizza.PizzaService;
import whz.pti.eva.domain.user.CurrentCustomer;

import javax.validation.Valid;

/**
 * The Class CartController.
 */
@Controller
public class CartController {

	/** The cart service. */
	private CartService cartService;

	/** The pizza service. */
	private PizzaService pizzaService;

	/** The new item form Validator. */
	private NewItemFormValidator newItemFormValidator;
	
    /** The smmp service. */
    private SmmpService smmpService;
	
	
	/**
	 * Instantiates a new cart controller.
	 *
	 * @param cartService the cart service
	 * @param pizzaService the pizza service
	 * @param newItemFormValidator the new item form validator
	 * @param smmpService the smmp service
	 */
	@Autowired
	public CartController(CartService cartService, PizzaService pizzaService, NewItemFormValidator newItemFormValidator,
			SmmpService smmpService) {
		this.cartService = cartService;
		this.pizzaService = pizzaService;
		this.newItemFormValidator = newItemFormValidator;
		this.smmpService = smmpService;
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
	 * @param model the model object
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
	 * @param newItemForm the new item form
	 * @param bindingResult the binding result
	 * @param model the model
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
	 * @param model the model object
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
	 * @param itemId Id of the item
	 * @param model the model object
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
	 * @param itemId Id of the item
	 * @param model the model object
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
	 * @param model the model
	 * @return the string
	 */

	@PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
	public String processCartToOrder(@RequestParam String from, @RequestParam String sume, Model model) {
		cartService.processCartWithUserIdToOrder(from);
		Cart cart = cartService.findByUserIdOrElseCreateAndReturn(from);
		String Preis = sume;
        PayActionResponse payActionResponse = smmpService.doPayAction(from, "whz", sume);
        cartService.savePayment(cart, payActionResponse.getDescription(), sume);
		cartService.deleteItemsInCartWithUserId(from);
		
		return "done";
	}
	

}
