package whz.pti.eva.ordered;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import whz.pti.eva.domain.cart.Item;
import whz.pti.eva.domain.ordered.Ordered;
import whz.pti.eva.domain.ordered.OrderedRepository;
import whz.pti.eva.domain.pizza.Pizza;
import whz.pti.eva.domain.pizza.PizzaSize;
import whz.pti.eva.services.ordered.OrderedService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OrderedServiceImplTest {

    /** The ordered service. */
    @Autowired
    private OrderedService orderedService;

    /** The ordered repository mock. */
    @MockBean
    private OrderedRepository orderedRepository;


    /**
     * Setup
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

        List<Ordered> orders = new ArrayList<>();
        Ordered order1 = new Ordered("existingUser", items);
        order1.setId(12L);
        orders.add(order1);

        when(orderedRepository.findAll()).thenReturn(orders);
        when(orderedRepository.findByUserId(any(String.class)))
                .thenAnswer((Answer<List<Ordered>>) invocation -> {
                    String userId = invocation.getArgument(0);
                    if ("existingUser".equals(userId)) {
                        return orders;
                    }
                    else {
                        return List.of();
                    }
                });
        when(orderedRepository.save(any(Ordered.class)))
                .thenAnswer((Answer<Ordered>) invocation -> {
                    Ordered order = invocation.getArgument(0);
                    order.setId(99L);
                    orders.add(order);
                    return order;
                });
        when(orderedRepository.findById(anyLong()))
                .thenAnswer((Answer<Optional<Ordered>>) invocation -> {
                    long orderedId = invocation.getArgument(0);
                    return orders.stream().filter(ordered -> ordered.getId() == orderedId).findFirst();
                });
    }

    /**
     * Test Order List
     */
    @Test
    public void testOrderList() {
        List<Ordered> orders = orderedService.orderList();

        verify(orderedRepository, times(1)).findAll();

        assertEquals(1, orders.size());
    }

    /**
     * Test Order List by UserID
     */
    @Test
    public void testOrderListByUserId() {
        List<Ordered> orders = orderedService.orderListByUserId("existingUser");

        verify(orderedRepository, times(1)).findByUserId(any(String.class));
        orders.forEach(ordered -> {
            assertEquals("existingUser", ordered.getUserId());
        });
    }

    /**
     * Test add Ordered
     */
    @Test
    public void testAddOrdered() {
        Pizza pizza = new Pizza("Veggie",new BigDecimal("1.20"),new BigDecimal("3.00"),new BigDecimal("5.50"));
        Item item = new Item(12, pizza, PizzaSize.SMALL);
        orderedService.addOrdered("newUser", Arrays.asList(item));

        verify(orderedRepository,times(1)).save(any(Ordered.class));

        List<Ordered> orders = orderedService.orderList();
        assertEquals(2, orders.size());
    }

    /**
     * Test Order List
     */
    @Test
    public void testCalculateSumOfOrdered(){
        BigDecimal sumFromExistingOrder = orderedService.calculateSumOfOrdered(12L);
        BigDecimal sumFromNonExistingOrder = orderedService.calculateSumOfOrdered(45L);

        verify(orderedRepository, times(2)).findById(anyLong());

        assertEquals(new BigDecimal("10.0"), sumFromExistingOrder);
        assertEquals(new BigDecimal("0.0"), sumFromNonExistingOrder);
    }
}
