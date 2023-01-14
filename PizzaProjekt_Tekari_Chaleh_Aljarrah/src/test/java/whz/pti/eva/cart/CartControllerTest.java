package whz.pti.eva.cart;

import org.hamcrest.Matchers;
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
import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.services.cart.CartService;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.services.pizza.PizzaService;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.Role;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The Class CartControllerTest.
 */
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CartControllerTest {

	/** The web app context. */
	@Autowired
	private WebApplicationContext webAppContext;

	/** The cart service mock. */
	@MockBean
	private CartService cartServiceMock;

	/** The pizza service mock. */
	@MockBean
	private PizzaService pizzaServiceMock;
	
	/** The mock mvc. */
	private MockMvc mockMvc;
	
	/** The user. */
	private CurrentCustomer user;

	/** The admin. */
	private CurrentCustomer admin;

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
		Customer adminCustomer = new Customer();
		adminCustomer.setLoginName("testAdmin");
		adminCustomer.setPasswordHash("test");
		adminCustomer.setRole(Role.ADMIN);

		user = new CurrentCustomer(userCustomer);
		admin = new CurrentCustomer(adminCustomer);

		List<Pizza> pizzas = List.of(
				new Pizza("Margarita",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50")), 
				new Pizza("Salami",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10")),
				new Pizza("Pepperoni",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10"))
				);
		List<Item> items = List.of(
				new Item(1, pizzas.get(0), PizzaSize.LARGE),
				new Item(2, pizzas.get(1), PizzaSize.MEDIUM),
				new Item(3, pizzas.get(2), PizzaSize.SMALL)
				);
		
		when(cartServiceMock.findByUserIdOrElseCreateAndReturn(anyString()))
		.thenAnswer((Answer<Cart>) invocation -> {
			return new Cart(invocation.getArgument(0), items);
		});

		when(cartServiceMock.calculateSumOfAllItemsInCartWithUserId(anyString()))
		.thenReturn(new BigDecimal("12.34"));

		doNothing()
		.when(cartServiceMock).addItemToCartWithUserId(anyString(), any(NewItemForm.class));

		doNothing()
		.when(cartServiceMock).removeItemFromCartWithUserItem(anyString(), anyLong());

		doNothing()
		.when(cartServiceMock).updatePizzaSizeFromItemInCart(anyString(), anyLong(), any(PizzaSize.class));

		doNothing()
		.when(cartServiceMock).updateQuantityFromItemInCart(anyString(), anyLong(), anyInt());

		doNothing()
		.when(cartServiceMock).processCartWithUserIdToOrder(anyString());

		doNothing()
		.when(cartServiceMock).deleteItemsInCartWithUserId(anyString());

		when(pizzaServiceMock.pizzaList())
		.thenReturn(pizzas);
	}
	
	/**
	 * Test current shopping cart.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCurrentShoppingCart() throws Exception {
		mockMvc.perform(
				get("/cart")
				.with(user(user)))
		.andExpect(status().isOk())
		.andExpect(view().name("Warenkorb"))
		.andExpect(model().attributeExists("cart", "sum"))
		.andExpect(model().attribute("cart", Matchers.hasProperty("items", hasSize(3))))
		.andExpect(model().attribute("sum", is(equalTo(new BigDecimal("12.34")))))
		.andDo(print());
	}

	/**
	 * Test shopping cart with user id success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testShoppingCartWithUserIdSuccess() throws Exception {
		mockMvc.perform(
				get("/cart/testuser")
				.with(user(admin)))
		.andExpect(status().isOk())
		.andExpect(view().name("Warenkorb"))
		.andExpect(model().attributeExists("cart", "sum"))
		.andExpect(model().attribute("cart", Matchers.hasProperty("items", hasSize(3))))
		.andExpect(model().attribute("sum", is(equalTo(new BigDecimal("12.34")))))
		.andDo(print());
	}
	
	/**
	 * Test shopping cart with user id failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testShoppingCartWithUserIdFailure() throws Exception {
		mockMvc.perform(
				get("/cart/admin")
				.with(user(user)))
		.andExpect(status().isForbidden())
		.andDo(print());
	}

	/**
	 * Test add item to cart success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testAddItemToCartSuccess() throws Exception {
		NewItemForm newItemForm = new NewItemForm();
		newItemForm.setPizzaId(0);
		newItemForm.setQuantity(2);
		newItemForm.setSize(PizzaSize.MEDIUM);

		mockMvc.perform(
				post("/addItemToCart")
				.with(user(user))
				.flashAttr("newItemForm", newItemForm))
		.andExpect(status().isOk())
		.andExpect(view().name("PizzaForever-App"))
		.andExpect(model().attributeExists("pizzas", "message"))
		.andExpect(model().attribute("pizzas", hasSize(3)))
		.andExpect(model().attribute("message", equalTo("Ihre Bestellung wurde hinzugefuegt, Sie koennen weitere hinzufuegen.")))
		.andExpect(model().attribute("newItemForm", isA(NewItemForm.class)))
		.andDo(print());

		verify(cartServiceMock, times(1)).addItemToCartWithUserId(anyString(), any(NewItemForm.class));
	}
	
	/**
	 * Test add item to cart failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testAddItemToCartFailure() throws Exception {
		NewItemForm newItemForm = new NewItemForm();
		newItemForm.setPizzaId(0);
		newItemForm.setQuantity(0);
		newItemForm.setSize(PizzaSize.MEDIUM);

		mockMvc.perform(
				post("/addItemToCart")
				.with(user(user))
				.flashAttr("newItemForm", newItemForm))
		.andExpect(status().isOk())
		.andExpect(view().name("PizzaForever-App"))
		.andExpect(model().attributeExists("pizzas", "error"))
		.andExpect(model().attribute("pizzas", hasSize(3)))
		.andExpect(model().attribute("error", equalTo("at least a quantity of one is needed!")))
		.andExpect(model().attribute("newItemForm", isA(NewItemForm.class)))
		.andDo(print());

		verify(cartServiceMock, times(0)).addItemToCartWithUserId(anyString(), any(NewItemForm.class));
	}

	/**
	 * Test remove item from cart.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRemoveItemFromCart() throws Exception {
		mockMvc.perform(
				post("/removeItemFromCart")
				.with(user(user))
				.param("itemId", "23"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/cart"))
		.andDo(print());

		verify(cartServiceMock, times(1)).removeItemFromCartWithUserItem(anyString(), anyLong());
	}

	/**
	 * Test update pizza size.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testUpdatePizzaSize() throws Exception {
		mockMvc.perform(
				post("/updatePizzaSize")
				.with(user(user))
				.param("size", PizzaSize.SMALL.toString())
				.param("itemId", "23"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/cart"))
		.andDo(print());

		verify(cartServiceMock, times(1)).updatePizzaSizeFromItemInCart(anyString(), anyLong(), any(PizzaSize.class));
	}

	/**
	 * Test update pizza quantity.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testUpdatePizzaQuantity() throws Exception {
		mockMvc.perform(
				post("/updatePizzaQuantity")
				.with(user(user))
				.param("quantity", "2")
				.param("itemId", "21"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/cart"))
		.andDo(print());

		verify(cartServiceMock, times(1)).updateQuantityFromItemInCart(anyString(), anyLong(), anyInt());
	}

	/**
	 * Test checkout.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCheckout() throws Exception {
		mockMvc.perform(
				post("/cart/checkout")
				.with(user(user)))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"))
		.andDo(print());

		verify(cartServiceMock, times(1)).processCartWithUserIdToOrder(anyString());
		verify(cartServiceMock, times(1)).deleteItemsInCartWithUserId(anyString());
	}
	
}
