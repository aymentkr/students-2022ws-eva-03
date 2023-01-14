package whz.pti.eva.domain.ordered;

import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class OrderItem.
 */
@Entity
public class OrderItem extends BaseEntity<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2325162909922373261L;

	/** The quantity. */
	private int quantity;
	
	/** the pizza. */
	@ManyToOne
	private Pizza pizza;
	
	/** The size. */
	@Enumerated(EnumType.STRING)
	private PizzaSize size;
	
	/** The price. */
	private BigDecimal price;


	/**
	 * Instantiates a new order item.
	 */
	public OrderItem() {
	}

	/**
	 * Instantiates a new order item.
	 *
	 * @param quantity the quantity
	 * @param pizza the pizza
	 * @param size the size
	 * @param price the price
	 */
	public OrderItem(int quantity, Pizza pizza, PizzaSize size, BigDecimal price) {
		this.quantity = quantity;
		this.pizza = pizza;
		this.size = size;
		this.price = price;
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
}
