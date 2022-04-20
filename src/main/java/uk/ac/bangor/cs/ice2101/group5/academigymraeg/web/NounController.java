package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Question;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.NounRequestRepository;

/**
 * Controller to process all Noun related pages for instructor, mainly CRUD
 * NounQuery handles all read requests
 * 
 * @author owenw
 *
 */
@Controller
@RequestMapping(value = "/nouns")
public class NounController {

	@Autowired
	private NounRequestRepository repo;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String nounRequestForm(Model uiModel) {
		uiModel.addAttribute("request", new NounRequest());

		uiModel.addAttribute("nounGenders", NounRequest.NounGenders.values());
		return "/nouns/nounform";
	}

	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String NounsRequest(Model uiModel, @ModelAttribute NounRequest request) {

		uiModel.addAttribute("request", request);
		repo.save(request);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(Model uiModel, @PathVariable("id") int primaryKey) {
		repo.deleteById(primaryKey);
		
		return "redirect:/nouns/list";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String nounEditGet(Model uiModel, @PathVariable("id") int id) {
		Optional<NounRequest> noun = repo.findById(id);
		
		uiModel.addAttribute("nountoedit", noun.get());
		uiModel.addAttribute("nounGenders", NounRequest.NounGenders.values());
		
		return "/nouns/nounedit";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String NounsEditPost(Model uiModel, @ModelAttribute NounRequest request) {

		request.setPrimaryKey(request.getPrimaryKey());
		repo.save(request);
		
		return "redirect:list";
	}
	




}
