package whz.pti.eva.domain.cart;

import whz.pti.eva.common.BaseEntity;
import whz.pti.eva.domain.ordered.OrderItem;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class Item.
 */
@Entity
@EntityListeners(ItemListener.class)
public class Item extends BaseEntity<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5465103857402101265L;

	/** The quantity. */
	private int quantity;
	
	/** The pizza. */
	@ManyToOne
	private Pizza pizza;
	
	/** The size. */
	@Enumerated(EnumType.STRING)
	private PizzaSize size;
	
	/** The price. */
	private BigDecimal price;

	
	/**
	 * Instantiates a new item.
	 */
	public Item() {
	}

	/**
	 * Instantiates a new item.
	 *
	 * @param quantity the quantity
	 * @param pizza the pizza
	 * @param size the size
	 */
	public Item(int quantity, Pizza pizza, PizzaSize size) {
		this.quantity = quantity;
		this.pizza = pizza;
		this.size = size;
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
	 * Gets the pizza.
	 *
	 * @return the pizza
	 */
	public Pizza getPizza() {
		return pizza;
	}

	/**
	 * Sets the pizza.
	 *
	 * @param pizza the new pizza
	 */
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	/**
	 * Gets the pizza size.
	 * 
	 * @return the pizza size
	 */
	public PizzaSize getSize() {
		return size;
	}
	
	/**
	 * Sets the pizza size.
	 * 
	 * @param size the new pizza size
	 */
	public void setSize(PizzaSize size) {
		this.size = size;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * Gets the single price from pizza and size.
	 *
	 * @return the single price from pizza and size
	 */
	public BigDecimal getSinglePriceFromPizzaAndSize() {
		switch (this.size) {
		case SMALL:
			return this.pizza.getPriceSmall();
		case MEDIUM:
			return this.pizza.getPriceMedium();
		case LARGE:
			return this.pizza.getPriceLarge();
		}
		return new BigDecimal("0");
	}
	
	/**
	 * Convert to order item.
	 *
	 * @return the order item
	 */
	public OrderItem convertToOrderItem() {
		return new OrderItem(this.quantity, this.pizza, this.size, this.price);
	}
}
