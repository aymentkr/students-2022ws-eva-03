package whz.pti.eva.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/** The user details service. */
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Configures the web security.
	 *
	 * @param http the Http request
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.authorizeRequests()
		.antMatchers("/login/**", "/static/css/**", "/", "/register/**").permitAll()
		.antMatchers("/users/m**", "/h2-console/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/", true)
		.failureUrl("/login?error")
		.usernameParameter("loginName")
		.permitAll()
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutUrl("/logout")
		.permitAll();
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}

	/**
	 * Configures the authentication process.
	 *
	 * @param auth the Authentication
	 * @throws Exception the exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
