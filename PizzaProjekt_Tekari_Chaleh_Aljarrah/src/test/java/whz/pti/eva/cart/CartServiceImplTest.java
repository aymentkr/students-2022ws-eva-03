package whz.pti.eva.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import whz.pti.eva.domain.cart.Cart;
import whz.pti.eva.domain.cart.CartRepository;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.cart.NewItemForm;
import whz.pti.eva.services.cart.CartService;
import whz.pti.eva.services.ordered.OrderedService;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

/**
 * The Class CartServiceImplTest.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CartServiceImplTest {

    /** The cart service. */
    @Autowired
    private CartService cartService;

    /** The cart repository mock. */
    @MockBean
    private CartRepository cartRepository;

    /** The Ordered service mock. */
    @MockBean
    private OrderedService orderedService;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        Pizza pizza1 = new Pizza("Margarita",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50"));
        Pizza pizza2 = new Pizza("Salami",new BigDecimal("1.10"),new BigDecimal("3.20"),new BigDecimal("5.10"));

        Item item1 = new Item();
        item1.setId(0L);
        item1.setPizza(pizza1);
        item1.setQuantity(3);
        item1.setSize(PizzaSize.SMALL);
        item1.setPrice(new BigDecimal("5.0"));

        Item item2 = new Item();
        item2.setId(1L);
        item2.setPizza(pizza2);
        item2.setQuantity(4);
        item2.setSize(PizzaSize.MEDIUM);
        item2.setPrice(new BigDecimal("5.0"));

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart("existingUser", items));

        when(cartRepository.findAll()).thenReturn(carts);
        when(cartRepository.existsByUserId(any(String.class)))
                .thenAnswer((Answer<Boolean>) invocation -> "existingUser".equals(invocation.getArgument(0)));
        when(cartRepository.findByUserId(any(String.class)))
                .thenAnswer((Answer<Optional<Cart>>) invocation -> {
                    String userId = invocation.getArgument(0);
                    if ("existingUser".equals(userId)) {
                        return Optional.of(carts.get(0));
                    }
                    return Optional.of(new Cart(userId));
                });
        when(cartRepository.findItemWithIdInCartWithUserId(any(String.class), anyLong()))
                .thenAnswer((Answer<Optional<Item>>) invocation -> {
                    String userId = invocation.getArgument(0);
                    if ("existingUser".equals(userId)) {
                        long itemId = invocation.getArgument(1);
                        return carts.get(0).getItems().stream().filter(item -> item.getId() == itemId).findFirst();
                    }
                    else {
                        return Optional.empty();
                    }
                });
    }

    /**
     * Test get all Carts.
     */
    @Test
    public void testGetAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        assertEquals(1, carts.size());
        verify(cartRepository, times(1)).findAll();
    }

    /**
     * Test Add Cart.
     */
    @Test
    public void testAddCart() {
        cartService.addCart("user01");
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    /**
     * Test Find by UserId or Else Create and Return.
     */
    @Test
    public void testFindByUserIdOrElseCreateAndReturn() {
        Cart cartFromExistingUser = cartService.findByUserIdOrElseCreateAndReturn("existingUser");
        Cart cartFromNewUser = cartService.findByUserIdOrElseCreateAndReturn("newUser");

        verify(cartRepository, times(2)).existsByUserId(any(String.class));
        verify(cartRepository, times(2)).findByUserId(any(String.class));

        assertEquals("existingUser", cartFromExistingUser.getUserId());
        assertEquals(2, cartFromExistingUser.getItems().size());
        assertEquals("newUser", cartFromNewUser.getUserId());
        assertEquals(0, cartFromNewUser.getItems().size());
    }

    /**
     * Test Calculate Sum of all Items in Cart with UserId.
     */
    @Test
    public void testCalculateSumOfAllItemsInCartWithUserId() {
        BigDecimal sumOfExistingUser = cartService.calculateSumOfAllItemsInCartWithUserId("existingUser");
        BigDecimal sumOfNewUser = cartService.calculateSumOfAllItemsInCartWithUserId("newUser");

        assertEquals(new BigDecimal("10.0"), sumOfExistingUser);
        assertEquals(new BigDecimal("0.0"), sumOfNewUser);
    }

    /**
     * Test Add Item to Cart with UserId.
     */
    @Test
    public void testAddItemToCartWithUserId() {
        NewItemForm newItemForm = new NewItemForm();
        newItemForm.setQuantity(3);
        newItemForm.setSize(PizzaSize.LARGE);
        newItemForm.setPizzaId(1);
        cartService.addItemToCartWithUserId("existingUser", newItemForm);

        Cart cartToCheck = cartService.findByUserIdOrElseCreateAndReturn("existingUser");

        assertEquals(3, cartToCheck.getItems().size());
    }

    /**
     * Test Remove Item from Cart with UserItem.
     */
    @Test
    public void testRemoveItemFromCartWithUserItem() {
        cartService.removeItemFromCartWithUserItem("existingUser", 0L);
        Cart cartToCheck = cartService.findByUserIdOrElseCreateAndReturn("existingUser");

        assertEquals(1, cartToCheck.getItems().size());
        for (Item itemToCheck : cartToCheck.getItems()) {
            assertNotEquals(0L, itemToCheck.getId());
        }
    }

    /**
     * Test Update Pizza size from item in cart.
     */
    @Test
    public void testUpdatePizzaSizeFromItemInCart() {
        cartService.updatePizzaSizeFromItemInCart("existingUser", 1L, PizzaSize.LARGE);
        Cart cartToCheck = cartService.findByUserIdOrElseCreateAndReturn("existingUser");
        Item itemToCheck = cartToCheck.getItems().stream().filter(item -> item.getId() == 1L).findFirst().orElseThrow();

        Assertions.assertEquals(PizzaSize.LARGE, itemToCheck.getSize());
    }

    /**
     * Test Update Quantity from Item in Cart.
     */
    @Test
    public void testUpdateQuantityFromItemInCart() {
        cartService.updateQuantityFromItemInCart("existingUser", 0L, 89);
        Cart cartToCheck = cartService.findByUserIdOrElseCreateAndReturn("existingUser");
        Item itemToCheck = cartToCheck.getItems().stream().filter(item -> item.getId() == 0L).findFirst().orElseThrow();

        assertEquals(89, itemToCheck.getQuantity());
    }

    /**
     * Test process Cart with UserId to Order.
     */
    @Test
    public void testProcessCartWithUserIdToOrder(){
        cartService.processCartWithUserIdToOrder("existingUser");

        verify(orderedService, times(1)).addOrdered(any(String.class), any(List.class));
    }

    /**
     * Test Delete Item in Cart with UserId.
     */
    @Test
    public void testDeleteItemsInCartWithUserId(){
        cartService.deleteItemsInCartWithUserId("existingUser");
        Cart cartToCheck = cartService.findByUserIdOrElseCreateAndReturn("existingUser");

        assertEquals(0, cartToCheck.getItems().size());
    }
}
