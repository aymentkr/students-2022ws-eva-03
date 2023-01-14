package whz.pti.eva.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The Interface CustomerRepository.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
    /**
     * Find one by email.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Customer> findCustomerByEmail(String email);
    
    /**
     * Find one by login name.
     *
     * @param loginName the login name
     * @return the optional
     */
    Optional<Customer> findByLoginName(String loginName);
    
    /**
     * Exists by email.
     *
     * @param email the email
     * @return true, if successful
     */
    boolean existsByEmail(String email);
    
    /**
     * Exists by login name.
     *
     * @param loginName the login name
     * @return true, if successful
     */
    boolean existsByLoginName(String loginName);

}
