package whz.pti.eva.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.services.pizza.PizzaService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: Auto-generated Javadoc
/**
 * The Class PizzaControllerTest.
 */
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class PizzaControllerTest {
	
	/** The web app context. */
	@Autowired
	private WebApplicationContext webAppContext;
	
	/** The pizza service mock. */
	@MockBean
	private PizzaService pizzaServiceMock;
	
	/** The mock mvc. */
	private MockMvc mockMvc;
	
	/**
	 * Setup.
	 */
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
		when(pizzaServiceMock.pizzaList())
		.thenReturn(List.of(
				new Pizza("Margarita",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50")),
				new Pizza("Salami",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10")),
				new Pizza("Pepperoni",new BigDecimal("1.00"),new BigDecimal("3.40"),new BigDecimal("5.80")),
				new Pizza("Veggie",new BigDecimal("1.50"),new BigDecimal("1.90"),new BigDecimal("2.40"))));
	}
	
	/**
	 * Test list all pizzas.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testListAllPizzas() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("PizzaForever-App"))
		.andExpect(model().attributeExists("pizzas","newItemForm"))
		.andExpect(model().attribute("pizzas", hasSize(4)))
		.andExpect(model().attribute("newItemForm", isA(NewItemForm.class)));
	}
	
}
