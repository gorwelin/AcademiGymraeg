package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Question;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Quiz;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.NounRequestRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.QuestionRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.QuizRepository;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

/**
 * Controller for all quiz related pages
 * only students are able to access these pages
 * 
 * @author owenw
 *
 */
@Controller
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired
	private NounRequestRepository nounRepo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private QuizRepository quizRepo;
	
	/**
	 * Generates a random question using the size of the Noun_Request table
	 * @return Randomly generated question
	 */
	@RequestMapping(value="/question/generate", method = RequestMethod.GET)
	public @ResponseBody Question generateQuestion() {
		
		long repoSize = nounRepo.count();
		int repoIndex = new Random().nextInt((int)repoSize) + 1;
		Optional<NounRequest> noun = nounRepo.findById(repoIndex);
		
		Question question = new Question(noun.get());
		questionRepo.save(question);
		return question;
	}
	
	/**
	 * Produces a random quiz using 20 random questions and saves it without a User attatched to it
	 * @return randomly generated quiz
	 */
	@RequestMapping(value="/generate", method = RequestMethod.GET)
	public @ResponseBody Quiz generateQuiz() {
		int amountOfQuestions = 20;
		
		List<Question> questionList = new LinkedList<Question>();
		
		for (int i = 0; i < amountOfQuestions; i++) {
			questionList.add(generateQuestion());
		}
		
		Quiz quiz = new Quiz(questionList);
		quizRepo.save(quiz);
		
		return quiz;
	}
	
	/**
	 * return which user is currently logged in
	 * @return User
	 */
	  @ResponseBody
	  public User currentUser() {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     User user = (User)authentication.getPrincipal();
	     return user;
	  }
	
	@RequestMapping(value="/take", method = RequestMethod.GET)
	public String viewQuiz(Model uiModel) {
		Quiz quiz = generateQuiz();
		
		uiModel.addAttribute("quiz", quiz);
		uiModel.addAttribute("quizID", quiz.getId());
		uiModel.addAttribute("questionslist", quiz.getQuestionList());
		
		List<String> inputAnswers = new LinkedList<>();
		uiModel.addAttribute("inputAnswers", inputAnswers);
		
		return "quiz/take";
	}
	
	/**
	 * Saves quiz to database using input from User and deletes any unsubmitted quizzes
	 * @param uiModel user interface the data is pushed to through GET
	 * @param formQuiz Quiz submitted through the website
	 * @return website page that shows the results of the quiz
	 */
	@RequestMapping(value="/take", method = RequestMethod.POST)
	public String resultsFromQuiz(Model uiModel, @ModelAttribute Quiz formQuiz) {
		
		Quiz quiz = quizRepo.findById(formQuiz.getId()).get();
		quiz.setUser(currentUser());
		List<Question> quizQuestions = quiz.getQuestionList();
		
		/**
		 * assigns the answers to the original quiz using the quiz submitted through the form.
		 * Removes the need to include <input type="hidden"> with answers and other details in html
		 */
		for (int i = 0; i < quizQuestions.size(); i++) {
			Question question = quizQuestions.get(i);
			question.setInputAnswer(formQuiz.getQuestionList().get(i).getInputAnswer());
			
			if(question.getInputAnswer().equalsIgnoreCase(question.getAnswer())) {
				question.setCorrect(true);
				quiz.incrementScore(1);
			}

		}
		
		
		quizRepo.save(quiz);
		
	 	// deletes any blank unsubmitted quizzes
		quizRepo.deleteAll(quizRepo.findByUserUserIDIsNull());
		
		return "redirect:results/" + quiz.getId();
	}
	

	
	
	/**
	 * Only allow currently logged in user to access results of quiz. Done through repo query and PreAuthorize to ensure user is valid
	 */
	@RequestMapping(value="/results/{quizid}", method = RequestMethod.GET)
	@PreAuthorize("@AuthenticationService.hasAccess(authentication, #quizid)")
	public String viewQuizResults(Model uiModel, @PathVariable("quizid") int quizid, Principal principal) {
		
		Quiz quiz = quizRepo.findByIdAndUserUserID(quizid, currentUser().getUserID());
		
		uiModel.addAttribute(quiz);

		return "quiz/results";
	}
	
	
	@RequestMapping(value = "/results/list", method = RequestMethod.GET)
	public String ViewQuizResultsList(Model uiModel) {
		
		uiModel.addAttribute("quizResultList", quizRepo.findByUserUserID(currentUser().getUserID()));

		return "quiz/resultList";

	}
	

}
