package whz.pti.eva.services.cart;

import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.domain.pizza.PizzaSize;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Interface CartService.
 */
public interface CartService {

    /**
     * Retrieves a list of all shopping carts.
     *
     * @return a list of shopping carts
     */
    List<Cart> getAllCarts();
    

	/**
	 * Adds a new shopping cart.
	 *
	 * @param userId the user id
	 */
	void addCart(String userId);

	/**
	 * Try to find a cart with a specific user id.
	 * 
	 * @param userId the user id
	 * 
	 * @return Cart the cart
	 */
	Cart findByUserIdOrElseCreateAndReturn(String userId);
	
	/**
	 * Adds a new Item to the shopping of a user.
	 *
	 * @param userId the user id of the user
	 * @param newItemForm the item to be added
	 */
	void addItemToCartWithUserId(String userId, NewItemForm newItemForm);
	
	/**
	 * Removes an existing item from the shopping cart of a user.
	 *
	 * @param userId the user id of the user
	 * @param itemId the item id of the item we want to remove
	 */
	void removeItemFromCartWithUserItem(String userId, long itemId);
	
	/**
	 * Updates the pizza size of an existing item in a shopping cart.
	 *
	 * @param userId the user id of the user
	 * @param itemId the item id of the item we want to update
	 * @param newPizzaSize the new pizza size
	 */
	void updatePizzaSizeFromItemInCart(String userId, long itemId, PizzaSize newPizzaSize);
	
	/**
	 * Updates the quantity of an existing item in a shopping cart.
	 *
	 * @param userId the user id of the user
	 * @param itemId the item id of the item we want to update
	 * @param newQuantity the new quantity
	 */
	void updateQuantityFromItemInCart(String userId, long itemId, int newQuantity);
	

	/**
	 * Calculates the sum of all items in a cart.
	 *
	 * @param userId the user id of the cart
	 * @return sum of all items
	 */
	BigDecimal calculateSumOfAllItemsInCartWithUserId(String userId);


	/**
	 * Convert to current cart to a new order.
	 *
	 * @param userId the given user id
	 */
	void processCartWithUserIdToOrder(String userId);
	
	/**
	 * Deletes all items in a cart with a given user id.
	 * 
	 * @param userId the user id
	 */
	void deleteItemsInCartWithUserId(String userId);
	
	void savePayment(Cart cart, String Preis, String From);
}
