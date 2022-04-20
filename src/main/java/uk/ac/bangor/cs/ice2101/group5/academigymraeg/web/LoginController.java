package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to map login to custom html page, processing handled by Spring Security
 * @author owenw
 *
 */
@Controller
public class LoginController {

	
	@RequestMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping(value = "/")
	public String defaultPage() {
		return "index";
	}
	


}
