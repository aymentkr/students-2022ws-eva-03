package whz.pti.eva.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaRepository;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.CustomerRepository;
import whz.pti.eva.domain.user.Role;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * The Class InitializeService.
 */
@Service
public class InitializeService {

	/** The pizza repository. */
	@Autowired
	private PizzaRepository pizzaRepository;
	
	/** The customer repository. */
	@Autowired
	private CustomerRepository customerRepository;
	
	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * Fill DB with some data like pizzas and the needed users for login.
	 */
	@Transactional
	public void initDatabase() {
		Pizza[] pizzas = new Pizza[] {
				new Pizza()
				.withName("Margarita")
				.withPriceSmall(new BigDecimal("1.20"))
				.withPriceMedium(new BigDecimal("3.00"))
				.withPriceLarge(new BigDecimal("5.50")),
				
				new Pizza()
				.withName("Napoli")
				.withPriceSmall(new BigDecimal("1.10"))
				.withPriceMedium(new BigDecimal("3.20"))
				.withPriceLarge(new BigDecimal("5.10")),
				
				new Pizza()
				.withName("Pepperoni")
				.withPriceSmall(new BigDecimal("1.00"))
				.withPriceMedium(new BigDecimal("3.40"))
				.withPriceLarge(new BigDecimal("5.80")),
				
				new Pizza()
				.withName("Veggie")
				.withPriceSmall(new BigDecimal("1.50"))
				.withPriceMedium(new BigDecimal("1.90"))
				.withPriceLarge(new BigDecimal("2.40")),

				new Pizza()
						.withName("Spinat")
						.withPriceSmall(new BigDecimal("1.20"))
						.withPriceMedium(new BigDecimal("3.00"))
						.withPriceLarge(new BigDecimal("5.50")),

				new Pizza()
						.withName("Tonno")
						.withPriceSmall(new BigDecimal("1.20"))
						.withPriceMedium(new BigDecimal("4.50"))
						.withPriceLarge(new BigDecimal("6.50")),

				new Pizza()
						.withName("Mozarella")
						.withPriceSmall(new BigDecimal("1.20"))
						.withPriceMedium(new BigDecimal("3.50"))
						.withPriceLarge(new BigDecimal("5.50")),
		};
		
		pizzaRepository.saveAll(List.of(pizzas));
		
		Customer admin = new Customer();
		admin.setFirstName("Admin");
		admin.setLastName("Administrator");
		admin.setLoginName("admin");
		admin.setEmail("admi@pizzaforever.com");
		admin.setPasswordHash(passwordEncoder.encode("a1"));
		admin.setRole(Role.ADMIN);
		admin.setActive(true);
		Customer customer1 = new Customer();
		customer1.setFirstName("User");
		customer1.setLastName("testuser");
		customer1.setLoginName("bnutz");
		customer1.setEmail("bnutz@pizzaforever.com");
		customer1.setPasswordHash(passwordEncoder.encode("n1"));
		customer1.setRole(Role.USER);
		customer1.setActive(true);
		Customer customer2 = new Customer();
		customer2.setFirstName("User");
		customer2.setLastName("testuser");
		customer2.setLoginName("cnutz");
		customer2.setEmail("cnutz@pizzaforever.com");
		customer2.setPasswordHash(passwordEncoder.encode("n2"));
		customer2.setRole(Role.USER);
		customer2.setActive(true);
		customerRepository.saveAll(Arrays.asList(admin, customer1, customer2));
	}
}
