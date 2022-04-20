package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.UserRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

/**
 * CRUD controller only accessibly by users with role of "ADMIN"
 * 
 * @author owenw
 *
 */
@RequestMapping(value = "/users")
@Controller
public class UserController {
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value = "/createuser", method = RequestMethod.GET)
	public String UserCreateForm(Model uiModel) {
		uiModel.addAttribute("user", new User());

		return "users/createuser";
	}
	
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public String saveUser(Model uiModel, @ModelAttribute User user) {
		
		uiModel.addAttribute("user", user);
		user.setPassword(pwEncoder.encode(user.getPassword()));
		user.getAuthorities();
		userRepo.save(user);
		
		return "redirect:/users/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String NounsList(Model uiModel) {
		uiModel.addAttribute("users", userRepo.findAll());

		return "users/userlist";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(Model uiModel, @PathVariable("id") long userID) {
		userRepo.deleteById(userID);
		
		return "redirect:/users/list";
	}
	
	@RequestMapping(value = "/resetpassword/{id}", method = RequestMethod.GET)
	public String resetUserPassword(Model uiModel, @PathVariable("id") long userID) {
		Optional<User> u = userRepo.findById(userID);
		
		u.get().setPassword(pwEncoder.encode("password"));
		userRepo.save(u.get());
		
		return "redirect:/users/list";
	}
	
	@RequestMapping(value = "/changerole", method = RequestMethod.POST)
	public String changeUserRole(@RequestParam("role") String role, @RequestParam("userID") long userID) {
		Optional<User> u = userRepo.findById(userID);
		
		u.get().setRole(role);
		userRepo.save(u.get());
		
		return "redirect:/users/list";
	}


}
