package whz.pti.eva.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import whz.pti.eva.domain.user.CurrentCustomer;
import whz.pti.eva.domain.user.Customer;
import whz.pti.eva.domain.user.CustomerCreateForm;
import whz.pti.eva.domain.user.Role;
import whz.pti.eva.services.user.customer.CustomerService;
import whz.pti.eva.services.user.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The class CustomerControllerTest for the customer controller tests.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CustomerControllerTest {

    /** The web app context. */
    @Autowired
    private WebApplicationContext webAppContext;

    /** The customer service mock. */
    @MockBean
    private CustomerService customerService;

    /** The mock mvc. */
    private MockMvc mockMvc;

    /** Tester with role user. */
    private CurrentCustomer user;

    /** Tester with role admin. */
    private CurrentCustomer admin;


    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
        Customer userCustomer = new Customer();
        userCustomer.setLoginName("tester");
        userCustomer.setPasswordHash("bsp");
        userCustomer.setRole(Role.USER);
        Customer adminCustomer = new Customer();
        adminCustomer.setLoginName("testAdmin");
        adminCustomer.setPasswordHash("test");
        adminCustomer.setRole(Role.ADMIN);

        user = new CurrentCustomer(userCustomer);
        admin = new CurrentCustomer(adminCustomer);

        when(customerService.getAllCustomers())
                .thenReturn(List.of(
                        new CustomerDTO(),
                        new CustomerDTO(),
                        new CustomerDTO()
                ));
        when(customerService.getCustomerByLoginName(any(String.class)))
                .then((Answer<Optional<Customer>>) invocation -> {
                    String userId = invocation.getArgument(0);
                    Customer customer = new Customer();
                    customer.setLoginName(userId);
                    return Optional.of(customer);
                });
    }

    /**
     * Test show all customers success.
     *
     * @throws Exception the exception
     */
    @Test
    public void testShowAllCustomersSuccess() throws Exception {
        mockMvc.perform(
                get("/customers").with(user(admin))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("Admin"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(3)));
    }

    /**
     * Test show all customers no authority.
     *
     * @throws Exception the exception
     */
    @Test
    public void testShowAllCustomersNoAuthority() throws Exception {
        mockMvc.perform(
                        get("/customers").with(user(user))
                )
                .andExpect(status().isForbidden());
    }

    /**
     * Test set state of customer success.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetStateOfCustomerSuccess() throws Exception {
        mockMvc.perform(
                post("/customer/setState")
                        .with(user(admin))
                        .param("userId", "temp")
                        .param("state", String.valueOf(true))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    /**
     * Test set state of customer no authority.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSetStateOfCustomerNoAuthority() throws Exception {
        mockMvc.perform(
                post("/customer/setState")
                        .with(user(user))
                        .param("userId", "temp")
                        .param("state", String.valueOf(true))
        )
                .andExpect(status().isForbidden());
    }

    /**
     * Test show user profile.
     *
     * @throws Exception the exception
     */
    @Test
    public void testShowUserProfile() throws Exception {
        mockMvc.perform(
                get("/my_profile").with(user(user))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("Profile"))
                .andExpect(model().attributeExists("currentCustomer", "customers"))
                .andExpect(model().attribute("customers", hasSize(1)));
    }

    /**
     * Test get register page.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetRegisterPage() throws Exception {

        mockMvc.perform(
                get("/register")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("UserForm"))
                .andExpect(model().attributeExists("userCreateForm"))
                .andExpect(model().attribute("userCreateForm", isA(CustomerCreateForm.class)));
    }

    /**
     * Test register success.
     *
     * @throws Exception the exception
     */
    @Test
    public void testRegisterSuccess() throws Exception {

        CustomerCreateForm form = new CustomerCreateForm();
        form.setFirstName("tester");
        form.setLastName("test");
        form.setEmail("tester@eva.com");
        form.setLoginName("tet");
        form.setPassword("123");
        form.setPasswordRepeated("123");

        mockMvc.perform(
                post("/register/confirm")
                        .flashAttr("userCreateForm", form)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
