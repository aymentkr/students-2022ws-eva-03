package whz.pti.eva.services.ordered;

import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.ordered.Ordered;

import java.math.BigDecimal;
import java.util.List;


/**
 * The Interface OrderedService.
 */
public interface OrderedService {
	
	/**
	 * Retrieves a list of all Orders.
	 *
	 * @return the list of all orders
	 */
	List<Ordered> orderList();
	
	/**
	 * Order list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Ordered> orderListByUserId(String userId);
	
	/**
	 * Adds one order.
	 *
	 * @param userId the user id
	 * @param items the items from the cart
	 */
	void addOrdered(String userId, List<Item> items);
	
	/**
	 * Calculates the sum of all items in the order.
	 *
	 * @param orderedId the ordered id
	 * @return the sum of the order
	 */
	BigDecimal calculateSumOfOrdered(long orderedId);
}
