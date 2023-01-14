package whz.pti.eva.domain.ordered;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * The listener interface for receiving ordered events.
 * The class that is interested in processing a ordered
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOrderedListener<code> method. When
 * the ordered event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OrderedEvent
 */
public class OrderedListener {

	/**
	 * Pre persist ordered.
	 *
	 * @param ordered the ordered
	 */
	@PrePersist
	public void prePersistOrdered(Ordered ordered) {
		setNumberOfItemsAccordingToOrderItemsSize(ordered);
	}

	/**
	 * Pre update ordered.
	 *
	 * @param ordered the ordered
	 */
	@PreUpdate
	public void preUpdateOrdered(Ordered ordered) {
		setNumberOfItemsAccordingToOrderItemsSize(ordered);
	}
	
	/**
	 * Sets the number of items according to order items size.
	 *
	 * @param ordered the new number of items according to order items size
	 */
	private void setNumberOfItemsAccordingToOrderItemsSize(Ordered ordered) {
		ordered.setNumberOfItems(ordered.getOrderItems().size());
	}
}
