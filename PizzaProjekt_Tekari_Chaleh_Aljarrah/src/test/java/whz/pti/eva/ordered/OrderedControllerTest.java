package whz.pti.eva.ordered;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.ordered.Ordered;
import whz.pti.eva.services.ordered.OrderedService;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.Role;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The Class OrderedControllerTest.
 */
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class OrderedControllerTest {
	
	/** The web app context. */
	@Autowired
	private WebApplicationContext webAppContext;
	
	/** The ordered service mock. */
	@MockBean
	private OrderedService orderedServiceMock;
	
	/** The mock mvc. */
	private MockMvc mockMvc;
	
	/** The user. */
	private CurrentCustomer user;
	
	/**
	 * Setup.
	 */
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
		Customer userCustomer = new Customer();
		userCustomer.setLoginName("testuser");
		userCustomer.setPasswordHash("bsp");
		userCustomer.setRole(Role.USER);
		
		user = new CurrentCustomer(userCustomer);
		List<Pizza> pizzas = List.of(
				new Pizza("Margarita",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50")), 
				new Pizza("Salami",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10")),
				new Pizza("Pepperoni",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10"))
				);
		List<Item> items = List.of(
				new Item(1, pizzas.get(0), PizzaSize.LARGE),
				new Item(2, pizzas.get(1), PizzaSize.MEDIUM)
				);
		when(orderedServiceMock.orderList())
		.thenReturn(List.of(new Ordered("existingUser", items)));
		
		when(orderedServiceMock.orderListByUserId(anyString()))
		.thenAnswer((Answer<List<Ordered>>) invocation -> {
			return List.of(new Ordered(invocation.getArgument(0), items));
		});
		
	}
	
	/**
	 * Test get all ordered by user id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetAllOrderedByUserId() throws Exception {
		mockMvc.perform(
				get("/my_orders")
				.with(user(user)))
		.andExpect(status().isOk())
		.andExpect(view().name("Orders"))
		.andExpect(model().attributeExists("orders"))
		.andExpect(model().attribute("orders", hasSize(1)))
		.andDo(print());
	}
}
