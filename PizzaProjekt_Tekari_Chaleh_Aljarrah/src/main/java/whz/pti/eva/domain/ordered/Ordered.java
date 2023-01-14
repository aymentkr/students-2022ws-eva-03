package whz.pti.eva.domain.ordered;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class Order.
 */
@Entity
@EntityListeners(OrderedListener.class)
public class Ordered extends BaseEntity<Long> implements Serializable {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3911159709836697108L;

	/** The number of items. */
	private int numberOfItems;
	
	/** The user id. */
	private String userId;
	
	/** The order items. */
	@OneToMany
	@Cascade(CascadeType.ALL)
	private List<OrderItem> orderItems;


	/**
	 * Instantiates a new order.
	 */
	public Ordered() {
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param userId the user which created this order
	 * @param items the items
	 */
	public Ordered(String userId, List<Item> items) {
		this.userId = userId;
		for (Item item : items) {
			this.addOrderItem(item.convertToOrderItem());
		}
		this.numberOfItems = items.size();
	}

	/**
	 * Gets the number of items.
	 *
	 * @return the number of items
	 */
	public int getNumberOfItems() {
		return numberOfItems;
	}

	/**
	 * Sets the number of items.
	 *
	 * @param numberOfItems the new number of items
	 */
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the order items.
	 *
	 * @return the order items
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	/**
	 * Sets the order items.
	 *
	 * @param orderItems the new order items
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	/**
	 * Adds one orderItem to the order.
	 * 
	 * @param orderItem the item to add
	 */
	public void addOrderItem(OrderItem orderItem) {
		if (this.orderItems == null) {
			this.orderItems = new LinkedList<>();
		}
		this.orderItems.add(orderItem);
	}
}
