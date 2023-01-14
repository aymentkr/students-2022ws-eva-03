package whz.pti.eva.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaRepository;
import whz.pti.eva.services.pizza.PizzaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class PizzaServiceImplTest {
	
	@Autowired
	private PizzaService pizzaService;

	@MockBean
	private PizzaRepository pizzaRepository;

	/**
	 * Setup
	 */
	@BeforeEach
	public void setup() {
		when(pizzaRepository.findAll())
		.thenReturn(List.of(
				new Pizza("Margarita",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50")),
				new Pizza("Salami",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10")),
				new Pizza("Pepperoni",new BigDecimal("1.00"),new BigDecimal("3.40"),new BigDecimal("5.80")),
				new Pizza("Veggie",new BigDecimal("1.50"),new BigDecimal("1.90"),new BigDecimal("2.40"))));
		
		when(pizzaRepository.findById(anyLong()))
		.thenAnswer((Answer<Optional<Pizza>>) invocation -> {
			Object[]args = invocation.getArguments();

			Pizza pizza = new Pizza();
			pizza.setId((long)args[0]);

			return Optional.of(pizza);
		});
	}

	/**
	 * Test Pizza List
	 */
	@Test
	public void testPizzaList() {
		List<Pizza> pizzas = pizzaService.pizzaList();
		assertEquals(4, pizzas.size());
	}

	/**
	 * Test get bi ID
	 */
	@Test
	public void testGetById() {
		Optional<Pizza> pizza = pizzaService.getPizzaById(12);
		assertEquals(12, pizza.get().getId());
	}

	/**
	 * Test Add Pizza
	 */
	@Test
	public void testAddPizza() {
		pizzaService.addPizza("Margarita", new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50"));
		verify(pizzaRepository,times(1)).save(any(Pizza.class));
	}

	/**
	 * Test Delete Pizza by ID
	 */
	@Test
	public void testDeletePizzaById() {
		pizzaService.deletePizzaById(90L);
		verify(pizzaRepository,times(1)).deleteById(90L);
	}
}
