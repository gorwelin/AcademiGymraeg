package uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz;

import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.web.NounRequest;

/**
 * Entity Question is called to generate a random quiz question each time a quiz is created
 * 
 * @author johnn
 * @author owenw
 *
 */
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="noun_id")
	private NounRequest noun;
	@Column
	private String questionType;
	@Column(updatable = false)
	private String question;
	@Column(updatable = false)
	private String answer;
	@Column
	private String inputAnswer;
	@Column
	private boolean correct;
	@Transient
	private String[] questionTypeArray = {"englishQ", "welshQ", "genderQ"};

	/**
	 * generates a random question
	 */
	public void generateQuestion() {
	    int randomElement = new Random().nextInt(questionTypeArray.length);
	    switch(questionTypeArray[randomElement]) {
	    case "englishQ":
	    	
	    	question = "What is the meaning of a Welsh Noun '" + noun.getWelshNoun() + "' in English?";
	    	answer = noun.getEnglishNoun();
	    	break;
	    case "welshQ":
	    	question = "What is the meaning of a English Noun '" + noun.getEnglishNoun() + "' in Welsh?";
	    	answer = noun.getWelshNoun();
	    	break;
	    case "genderQ":
	    	question = "What is the gender of the Welsh noun '" + noun.getWelshNoun() + "'?";
	    	answer = noun.getNounGenders().toString();
	    	break;
	    }
	    questionType = questionTypeArray[randomElement];
	}
	
	
	/**
	 * Constructor assigns values of object Question
	 * @param nounFromRepo Noun from NounRepository, shouldn't be called in @ Entity
	 */
	public Question(NounRequest nounFromRepo) {
		noun = nounFromRepo;
		generateQuestion();
		correct = false;
	}
	
	public Question() {
		
	}


	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public NounRequest getNoun() {
		return noun;
	}


	public void setNoun(NounRequest noun) {
		this.noun = noun;
	}


	public String getQuestionType() {
		return questionType;
	}


	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getInputAnswer() {
		return inputAnswer;
	}


	public void setInputAnswer(String inputAnswer) {
		this.inputAnswer = inputAnswer;
	}


	public boolean isCorrect() {
		return correct;
	}


	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


	public String[] getQuestionTypeArray() {
		return questionTypeArray;
	}


	public void setQuestionTypeArray(String[] questionTypeArray) {
		this.questionTypeArray = questionTypeArray;
	}


	@Override
	public String toString() {
		return "answer {" + answer + "}, user input {" + inputAnswer + "}, correct{" + correct + "} \n";
	}

	
	
}
