package whz.pti.eva.domain.cart;

import whz.pti.eva.domain.pizza.PizzaSize;

/**
 * The Class NewItemForm.
 */
public class NewItemForm {
	
	/** The quantity. */
	private int quantity;
	
	/** The size. */
	private PizzaSize size;
	
	/** The pizza id. */
	private long pizzaId;
	
	
	/**
	 * Instantiates a new new item form.
	 */
	public NewItemForm() {
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
	 * Gets the size.
	 *
	 * @return the size
	 */
	public PizzaSize getSize() {
		return size;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(PizzaSize size) {
		this.size = size;
	}
	
	/**
	 * Gets the pizza id.
	 *
	 * @return the pizza id
	 */
	public long getPizzaId() {
		return pizzaId;
	}
	
	/**
	 * Sets the pizza id.
	 *
	 * @param pizzaId the new pizza id
	 */
	public void setPizzaId(long pizzaId) {
		this.pizzaId = pizzaId;
	}
}
