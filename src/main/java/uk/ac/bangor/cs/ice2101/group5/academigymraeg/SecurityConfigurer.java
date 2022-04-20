package uk.ac.bangor.cs.ice2101.group5.academigymraeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.RepositoryBasedUserDetailsService;

/**
 * AcademiGymraegs configuration for the web application
 * only a user with the correct role can access their section of the website e.g. instructors can perform CRUD on nouns but not users
 * bcrypt used for new users to ensure security of password
 * Spring security mitigates risk of SQL injection by preventing malicious input such as including ";" to initiate query
 * 
 * @author owenw
 *
 */
@Component
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RepositoryBasedUserDetailsService userDetailsService;
	
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll();
		
		http.authorizeRequests()
		.antMatchers("/users/**")
		.hasAnyRole("ADMIN");
		
		http.authorizeRequests()
		.antMatchers("/nouns/**")
		.hasAnyRole("INSTRUCTOR");
		
		http.authorizeRequests()
		.antMatchers("/quiz/**")
		.hasAnyRole("STUDENT");
		
		http.formLogin().loginPage("/login").loginProcessingUrl("/process_login")
											.permitAll();
		
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
		
		http.requestCache();
		
		http.userDetailsService(userDetailsService());
	}
	
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean // The return from this method also registered in context
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
}
