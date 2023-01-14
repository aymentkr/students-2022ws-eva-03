package whz.pti.eva.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * The Interface CartRepository.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Find a cart by user id.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<Cart> findByUserId(String userId);
    
    /**
     * Checks if a cart with a given user id is present.
     * 
     * @param userId the user id
     * @return true, if exists
     */
    boolean existsByUserId(String userId);
    
    /**
     * Find item with id in cart with user id.
     *
     * @param userId the user id
     * @param itemId the item id
     * @return the optional
     */
    @Query("select i from Item i, Cart c where c.userId = ?1 and i.id = ?2")
    Optional<Item> findItemWithIdInCartWithUserId(String userId, long itemId);
}
