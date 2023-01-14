package whz.pti.eva.services.ordered;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.ordered.OrderItem;
import whz.pti.eva.domain.ordered.Ordered;
import whz.pti.eva.domain.ordered.OrderedRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The Class OrderedServiceImpl.
 */
@Service
public class OrderedServiceImpl implements OrderedService {
	
	/** The ordered repository. */
	private OrderedRepository orderedRepository;

	
	/**
	 * Instantiates a new ordered service impl.
	 *
	 * @param orderedRepository the ordered repository
	 */
	@Autowired
	public OrderedServiceImpl(OrderedRepository orderedRepository) {
		this.orderedRepository = orderedRepository;
	}
	
	/**
	 * Order list.
	 *
	 * @return the list
	 */
	@Override
	public List<Ordered> orderList() {
        return orderedRepository.findAll();
    }


	/**
	 * Order list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<Ordered> orderListByUserId(String userId) {
		return orderedRepository.findByUserId(userId);
	}
    
	/**
	 * Adds the ordered.
	 *
	 * @param userId the user id
	 * @param items the items
	 */
	@Override
	public void addOrdered(String userId, List<Item> items) {
		orderedRepository.save(new Ordered(userId, items));
	}

	/**
	 * Calculate sum of ordered.
	 *
	 * @param orderedId the ordered id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal calculateSumOfOrdered(long orderedId) {
		Optional<Ordered> ordered = orderedRepository.findById(orderedId);
		
		if (ordered.isPresent()) {
			BigDecimal sum = new BigDecimal("0.0");
			for (OrderItem orderItem : ordered.get().getOrderItems()) {
				sum = sum.add(orderItem.getPrice());
			}

			return sum;
		}
		else {
			return new BigDecimal("0.0");
		}
	}
}
