package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Uses Spring Boot's ErrorController interface to return pages for when
 * the most common http errors occur e.g. 403 forbidden
 * 
 * @author owenw
 * @author ethanq
 *
 */
@Controller
public class AGErrorController implements ErrorController {

	/**
	 * Returns a template to use for /error dependent on which HTTP error is shown.
	 * Will show error on url of the request e.g. localhost:8080/nouns/list for 403 if
	 * user isn't logged in as instructor
	 * 
	 * @param request gets http request header to access status_code
	 * @return link to file used for ui output template or redirect to home
	 */
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	      
	      if(statusCode != null) {
	    	  switch(statusCode) {
	    	  
	    	  case 401:
	    		  return "errors/error-401";
	    	  case 400:
	    		  return "errors/error-400";
	    	  case 404:
	    		  return "errors/error-404";
	    	  case 403:
	    		  return "errors/error-403";
	    	  case 500:
	    		  return "errors/error-500";
	    	default:
	    		return "errors/error";
	    	  }
	      } else {
	    	  return "redirect:/";
	      }
	      
	     
	}
	
	
	@Override
	public String getErrorPath() {
		return null;
	}

}
