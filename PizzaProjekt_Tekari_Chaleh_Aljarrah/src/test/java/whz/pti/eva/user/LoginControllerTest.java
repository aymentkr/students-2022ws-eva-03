package whz.pti.eva.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The class LoginControllerTest for the login controller tests.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LoginControllerTest {

    /** The web app context. */
    @Autowired
    private WebApplicationContext webAppContext;

    /** The mock mvc. */
    private MockMvc mockMvc;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(springSecurity()).build();
    }

    /**
     * Test if the login endpoint is accessible.
     *
     * @throws Exception if error occurs
     */
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                get("/login")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("UserForm"));
    }

    /**
     * Test if the logout endpoint is accessible.
     *
     * @throws Exception if error occurs
     */
    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(
                get("/logout")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
