package whz.pti.eva.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * The Class LoginController.
 */
@Controller
public class LoginController {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Gets the login page.
	 *
	 * @param error the error
	 * @param model the model
	 * @return the login page
	 */
	@GetMapping(path = "/login")
	public String getLoginPage(@RequestParam Optional<String> error, Model model) {
		log.debug("Hallo bei Pizza Forever");
		return "UserForm";
	}
	
	/**
	 * Logs out the user.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return redirect to the main page
	 */
	@GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
          
        return "redirect:/";
    }
}
