package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.NounRequestRepository;

/**
 * Manages all queries needed for getting nouns from the database
 * 
 * @author benw
 * @author owenw
 * 
 */
@RequestMapping(value = "/nouns")
@Controller
public class NounQueryController {

	/**
	 * instance injected into NounRequest Class
	 */
	@Autowired
	private NounRequestRepository repo;

	/**
	 * Pulls a list of all nouns
	 * @param uiModel user interface the data is pushed to through GET
	 * @return name of html file to add the attribute to (src/main/resources/templates/...)
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String NounsList(Model uiModel) {
		uiModel.addAttribute("requests", repo.findAll());
		uiModel.addAttribute("nounCount", repo.count());
		return "/nouns/nounsList";
	}

	/**
	 * 	Requests all the nouns with the same gender as the gender that has been
	 *  specified.
	 * @param uiModel user interface the data is pushed to through GET
	 * @param nounGenders which gender the list should view
	 * @return name of html file to add the attribute to
	 */
	@RequestMapping(value = "/list/find/gender/{nounGenders}", method = RequestMethod.GET)
	public String NounsListFindGender(Model uiModel, @PathVariable("nounGenders") String nounGenders) {
		uiModel.addAttribute("requests", repo.findByNounGenders(nounGenders));

		return "/nouns/nounsList";

	}
	
	/**
	 * Finds any and all nouns from the english noun passed as a parameter, not case sensitive
	 * @param uiModel user interface the data is pushed to through GET
	 * @param value english noun to search for
	 * @return name of html file to add the attribute to (src/main/resources/templates/...)
	 */
	@RequestMapping(value = "/list/find/english/{value}", method = RequestMethod.GET)
	public String NounsListFindEnglish(Model uiModel, @PathVariable("value") String value) {
		uiModel.addAttribute("requests", repo.findByEnglishNoun(value));

		return "/nouns/nounsList";

	}
	
	/**
	 * Finds any and all nouns from the welsh noun passed as a parameter, not case sensitive
	 * @param uiModel user interface the data is pushed to through GET
	 * @param value welsh noun to search for
	 * @return name of html file to add the attribute to (src/main/resources/templates/...)
	 */
	@RequestMapping(value = "/list/find/welsh/{value}", method = RequestMethod.GET)
	public String NounsListFindWelsh(Model uiModel, @PathVariable("value") String value) {
		uiModel.addAttribute("requests", repo.findByWelshNoun(value));

		return "/nouns/nounsList";

	}

	/**
	 * Requests a list of all the nouns in order of English nouns.
	 * @param uiModel user interface the data is pushed to through GET
	 * @return name of html file to add the attribute to
	 */
	@RequestMapping(value = "/list/sort/english", method = RequestMethod.GET)
	public String NounsListEnglishNoun(Model uiModel) {
		uiModel.addAttribute("requests", repo.findAllByOrderByEnglishNoun());

		return "/nouns/nounsList";
	}

	/**
	 * Requests a list of all the nouns in order of Welsh nouns.
	 * @param uiModel user interface the data is pushed to through GET
	 * @return name of html file to add the attribute to
	 */
	@RequestMapping(value = "/list/sort/welsh", method = RequestMethod.GET)
	public String NounsListWelshNoun(Model uiModel) {
		uiModel.addAttribute("requests", repo.findAllByOrderByWelshNoun());

		return "/nouns/nounsList";
	}


}
