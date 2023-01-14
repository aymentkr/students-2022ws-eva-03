package whz.pti.eva.domain.cart;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;

/**
 * The listener interface for receiving item events.
 * The class that is interested in processing a item
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addItemListener<code> method. When
 * the item event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ItemEvent
 */
public class ItemListener {

	/**
	 * Before any update.
	 *
	 * @param item the item
	 */
	@PrePersist
	@PreUpdate
	public void beforeAnyUpdate(Item item) {
		BigDecimal price = new BigDecimal(0);
		switch (item.getSize()) {
		case LARGE:
			price = item.getPizza().getPriceLarge();
			break;
		case MEDIUM:
			price = item.getPizza().getPriceMedium();
			break;
		case SMALL:
			price = item.getPizza().getPriceSmall();
			break;
		}
		
		item.setPrice(price.multiply(new BigDecimal(item.getQuantity())));
	}
}
