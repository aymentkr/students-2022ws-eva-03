package whz.pti.eva.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import whz.pti.eva.domain.user.*;
import whz.pti.eva.services.user.customer.CustomerService;
import whz.pti.eva.services.user.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;


    @BeforeEach
    public void setup() {
        Customer customer1 = new Customer("John", "Miller", "jmil", "john@eva.com", "123", Role.USER, new ArrayList<>(), true);
        customer1.setId(0L);
        Customer customer2 = new Customer("Lisa", "Stark", "lst", "lisa@eva.com", "abc", Role.ADMIN, new ArrayList<>(), true);
        customer2.setId(1L);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.findById(anyLong()))
                .thenAnswer((Answer<Optional<Customer>>) invocation -> {
                    long customerId = invocation.getArgument(0);
                    return customers.stream().filter(customer -> customer.getId() == customerId).findFirst();
                });
    }

    /**
     * Test get all Customers
     */
    @Test
    public void testGetAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();

        verify(customerRepository, times(1)).findAll();

        assertEquals(2, customers.size());
    }

    /**
     * Test Get Customer by ID
     */
    @Test
    public void testGetCustomerById() {
        assertThrows(NoSuchElementException.class, () -> customerService.getCustomerById(55L));

        assertDoesNotThrow(() -> customerService.getCustomerById(0L));
        CustomerDTO customerToCheck = customerService.getCustomerById(0L);
        assertEquals(0L, customerToCheck.getId());

        verify(customerRepository, times(3)).findById(anyLong());
    }

    /**
     * Test Get Customer by Email
     */
    @Test
    public void testGetCustomerByEmail() {
        customerService.getCustomerByEmail("john@gmail.com");

        verify(customerRepository, times(1)).findCustomerByEmail(anyString());
    }

    /**
     * Test Get Customer by Login Name
     */
    @Test
    public void testGetCustomerByLoginName() {
        customerService.getCustomerByLoginName("johnbush12");

        verify(customerRepository, times(1)).findByLoginName(anyString());
    }

    /**
     * Test Exists by Email
     */
    @Test
    public void testExistsByEmail() {
        customerService.existsByEmail("john@gmail.com");

        verify(customerRepository, times(1)).existsByEmail(anyString());
    }

    /**
     * Test Exists by Login Name
     */
    @Test
    public void testExistsByLoginName() {
        customerService.existsByLoginName("johnbush12");

        verify(customerRepository, times(1)).existsByLoginName(anyString());
    }

    /**
     * Test for adding a new customer
     */
    @Test
    public void testAddNewCustomer() {
        customerService.addNewCustomer(new Customer());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    /**
     * Test setting the state of an existing Customer
     */
    @Test
    public void testSetStateOfCustomerWithId() {
        customerService.setActivatedStateOfCustomerWithUserId("johnbush12", true);
        customerService.setActivatedStateOfCustomerWithUserId("johnbush12", false);

        verify(customerRepository, times(2)).findByLoginName(anyString());
    }

    /**
     * Test for adding a delivery address to the customer with id
     */
    @Test
    public void addDeliveryAddressToCustomerWithId() {
        customerService.addDeliveryAddressToCustomerWithId(0L, new DeliveryAddress());

        verify(customerRepository, times(1)).findById(anyLong());
    }


    /**
     * Test for creating an new Customer.
     */
    @Test
    public void testCreate() {
        CustomerCreateForm form = new CustomerCreateForm();
        form.setEmail("test@tester.com");
        form.setFirstName("tester");
        form.setLastName("test");
        form.setLoginName("tet");
        form.setPassword("123");
        form.setDeliveryAddresses(new ArrayList<>());

        Customer customer = customerService.create(form);
        assertEquals(form.getEmail(), customer.getEmail());
        assertEquals(form.getFirstName(), customer.getFirstName());
        assertEquals(form.getLastName(), customer.getLastName());
        assertEquals(form.getLoginName(), customer.getLoginName());
        assertNotEquals(form.getPassword(), customer.getPasswordHash());
        assertEquals(form.getDeliveryAddresses().size(), customer.getDeliveryAddresses().size());
    }
}
