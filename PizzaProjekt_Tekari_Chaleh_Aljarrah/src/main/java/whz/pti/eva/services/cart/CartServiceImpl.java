package whz.pti.eva.services.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.CartRepository;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.services.ordered.OrderedService;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.services.pizza.PizzaService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class CartServiceImpl.
 */
@Service
public class CartServiceImpl implements CartService {

	/** The cart repository. */
	private CartRepository cartRepository;
	
	/**  The pizza service. */
	private PizzaService pizzaService;
	
	/**  The ordered service. */
	private OrderedService orderedService;
	
    /** The Payment arrchiv. */
    Map<String, String> PaymentArrchiv = new HashMap<>();

	/**
	 * Instantiates a new cart service impl.
	 *
	 * @param cartRepository the cart repository
	 * @param pizzaService the pizza service
	 * @param orderedService the ordered service
	 */
	@Autowired
	public CartServiceImpl(CartRepository cartRepository, PizzaService pizzaService, OrderedService orderedService) {
		this.cartRepository = cartRepository;
		this.pizzaService = pizzaService;
		this.orderedService = orderedService;
	}
	
	/**
	 * Gets all the carts in the db.
	 *
	 * @return the list of carts
	 */
	@Override
	public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    
    

	/**
	 * Adds a new cart to the db.
	 *
	 * @param userId the user id
	 */
	@Override
	public void addCart(String userId) {
		cartRepository.save(new Cart(userId));
	}

	/**
	 * Try to find a cart with a specific user id.
	 *
	 * @param userId the user id to search for
	 * @return Optional object which may contains the found cart
	 */
	@Override
	public Cart findByUserIdOrElseCreateAndReturn(String userId) {
		if (!cartRepository.existsByUserId(userId)) {
			this.addCart(userId);
		}
		return cartRepository.findByUserId(userId).get();
	}

	/**
	 * Calculate sum of all items in cart with user id.
	 *
	 * @param userId the user id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal calculateSumOfAllItemsInCartWithUserId(String userId) {
		Cart cart = this.findByUserIdOrElseCreateAndReturn(userId);
		
		BigDecimal sum = new BigDecimal("0.0");
		for (Item item : cart.getItems()) {
			sum = sum.add(item.getPrice());
		}

		return sum;
	}

	/**
	 * Adds the item to cart with user id.
	 *
	 * @param userId the user id
	 * @param newItemForm the new item form
	 */
	@Override
	@Transactional
	public void addItemToCartWithUserId(String userId, NewItemForm newItemForm) {
		Cart cart = this.findByUserIdOrElseCreateAndReturn(userId);
		Optional<Pizza> optionalPizza = pizzaService.getPizzaById(newItemForm.getPizzaId());
		if (optionalPizza.isEmpty()) {
			return;
		}

		Item item = new Item();
		item.setPizza(optionalPizza.get());
		item.setQuantity(newItemForm.getQuantity());
		item.setSize(newItemForm.getSize());
		
		cart.addItem(item);
	}

	/**
	 * Removes the item from cart with user item.
	 *
	 * @param userId the user id
	 * @param itemId the item id
	 */
	@Override
	@Transactional
	public void removeItemFromCartWithUserItem(String userId, long itemId) {
		Cart cart = findByUserIdOrElseCreateAndReturn(userId);
		cart.getItems().removeIf((item) -> item.getId() == itemId);
	}

	/**
	 * Update pizza size from item in cart.
	 *
	 * @param userId the user id
	 * @param itemId the item id
	 * @param newPizzaSize the new pizza size
	 */
	@Override
	@Transactional
	public void updatePizzaSizeFromItemInCart(String userId, long itemId, PizzaSize newPizzaSize) {
		Optional<Item> itemInCartToUpdate = cartRepository.findItemWithIdInCartWithUserId(userId, itemId);
		itemInCartToUpdate.ifPresent(item -> item.setSize(newPizzaSize));
	}

	/**
	 * Update quantity from item in cart.
	 *
	 * @param userId the user id
	 * @param itemId the item id
	 * @param newQuantity the new quantity
	 */
	@Override
	@Transactional
	public void updateQuantityFromItemInCart(String userId, long itemId, int newQuantity) {
		Optional<Item> itemQuantityInCartToUpdate = cartRepository.findItemWithIdInCartWithUserId(userId,itemId);
		itemQuantityInCartToUpdate.ifPresent(item -> item.setQuantity(newQuantity));
	}
	
	/**
	 * Process cart with user id to order.
	 *
	 * @param userId the user id
	 */
	@Override
	@Transactional
	public void processCartWithUserIdToOrder(String userId) {
		Optional<Cart> optionalCartToProcess = cartRepository.findByUserId(userId);
		optionalCartToProcess.ifPresent((cartToProcess) -> {
			if ((cartToProcess.getItems() != null) && cartToProcess.getItems().size() > 0) {
				orderedService.addOrdered(userId, cartToProcess.getItems());
			}
		});
	}
	
	/**
	 * Delete item in cart with user id.
	 *
	 * @param userId the user id
	 */
	@Override
	@Transactional
	public void deleteItemsInCartWithUserId(String userId) {
		Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
		optionalCart.ifPresent((cart) -> cart.getItems().clear());
	}

	/**
	 * Save payment.
	 *
	 * @param cart the cart
	 * @param Preis the preis
	 * @param From the from
	 */
	@Override
	public void savePayment(Cart cart, String Preis, String From) {
		PaymentArrchiv.put(From, Preis);
	}
}
