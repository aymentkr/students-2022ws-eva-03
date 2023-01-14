package whz.pti.eva.domain.cart;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class Cart.
 */
@Entity
public class Cart extends BaseEntity<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1509821035263905654L;

	/** The quantity. */
	private int quantity;
	
	/** The user id. */
	private String userId;
	
	/** The items. */
	@OneToMany
	@Cascade(CascadeType.ALL)
	private List<Item> items;

	/**
	 * Instantiates a new cart.
	 */
	public Cart() {
	}
	/**
	 * Instantiates a new cart.
	 * 
	 * @param userId the user id to which the cart belongs
	 * @param items the items itself
	 */
	public Cart(String userId, List<Item> items) {
		this.userId = userId;
		this.items = items;
		this.quantity = items.size();
	}
	
	/**
	 * Instantiates a new cart.
	 *
	 * @param userId the user id to which the cart belongs
	 */
	public Cart(String userId) {
		this.userId = userId;
		this.items = new LinkedList<Item>();
		this.quantity = 0;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}
	
	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
		this.setQuantity(items.size());
	}
	
	/**
	 * Adds one item to the cart.
	 * 
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		this.items.add(item);
		this.setQuantity(this.items.size());
	}
}
