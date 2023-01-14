package whz.pti.eva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.services.pizza.PizzaService;

import java.util.List;

/**
 * The Class PizzaController.
 */
@Controller
public class PizzaController {

	/** The pizza service. */
	private PizzaService pizzaService;
	
	
	/**
	 * Instantiates a new pizza controller.
	 *
	 * @param pizzaService the pizza service
	 */
	@Autowired
	public PizzaController(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}
	
	/**
	 * List all pizzas.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(path = "/")
	public String listAllPizzas(Model model) {
		List<Pizza> pizzas = pizzaService.pizzaList();
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("newItemForm", new NewItemForm());
		
		return "PizzaForever-App";
	}
}
