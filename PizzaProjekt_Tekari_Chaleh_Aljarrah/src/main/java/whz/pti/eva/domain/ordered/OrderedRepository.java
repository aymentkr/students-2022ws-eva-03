package whz.pti.eva.domain.ordered;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The Interface OrderRepository.
 */
public interface OrderedRepository extends JpaRepository<Ordered, Long> {

	/**
	 * Find all orders from a given user.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Ordered> findByUserId(String userId);

}
