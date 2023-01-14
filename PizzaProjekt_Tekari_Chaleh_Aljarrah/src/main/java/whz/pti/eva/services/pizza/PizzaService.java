package whz.pti.eva.services.pizza;

import whz.pti.eva.domain.pizza.Pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The Interface PizzaService.
 */
public interface PizzaService {
	
	/**
	 * Retrieves a list of all pizzas.
	 *
	 * @return the list of pizzas
	 */
	List<Pizza> pizzaList();
	
	/**
	 * Gets the pizza with a given id.
	 * 
	 * @param pizzaId the pizza id
	 * @return Optional object which contains the resulting pizza if present
	 */
	Optional<Pizza> getPizzaById(long pizzaId);
	
	/**
	 * Adds one pizza.
	 *
	 * @param name the name
	 * @param priceLarge the price large
	 * @param priceMedium the price medium
	 * @param priceSmall the price small
	 */
	void addPizza(String name, BigDecimal priceLarge, BigDecimal priceMedium, BigDecimal priceSmall);

	/**
	 * Deletes a pizza from the db.
	 * 
	 * @param id the id of the pizza to delete
	 */
	void deletePizzaById(long id);
}
