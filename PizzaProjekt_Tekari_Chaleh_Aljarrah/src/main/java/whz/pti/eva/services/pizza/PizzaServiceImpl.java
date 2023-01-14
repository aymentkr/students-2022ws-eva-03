package whz.pti.eva.services.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The Class PizzaServiceImpl.
 */
@Service
public class PizzaServiceImpl  implements PizzaService {
	
	/** The pizza repository. */
	private PizzaRepository pizzaRepository;
	
	/**
	 * Instantiates a new pizza service impl.
	 *
	 * @param pizzaRepository the pizza repository
	 */
	@Autowired
	public PizzaServiceImpl(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	/**
	 * Retrieves a list of all pizzas.
	 *
	 * @return the list of pizzas
	 */
	@Override
	public List<Pizza> pizzaList() {
		return pizzaRepository.findAll();
	}
	
	/**
	 * Gets the pizza with a given id.
	 * 
	 * @param pizzaId the pizza id
	 * @return Optional object which contains the resulting pizza if present
	 */
	@Override
	public Optional<Pizza> getPizzaById(long pizzaId) {
		return pizzaRepository.findById(pizzaId);
	}
	
	/**
	 * Adds one pizza.
	 *
	 * @param name the name
	 * @param priceLarge the price large
	 * @param priceMedium the price medium
	 * @param priceSmall the price small
	 */
	@Override
	public void addPizza(String name, BigDecimal priceLarge, BigDecimal priceMedium, BigDecimal priceSmall) {
		pizzaRepository.save(new Pizza(name, priceLarge, priceMedium, priceSmall));
	}

	/**
	 * Deletes a pizza from the db.
	 * 
	 * @param id the id of the pizza to delete
	 */
	@Override
	public void deletePizzaById(long id) {
		pizzaRepository.deleteById(id);
	}
}
