package uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

/**
 * Spring JPA entity, java object that is also presented in a database as a table. Each instance is a row in said table
 * Quiz entity handles all input, processing and output for a student to take a quiz of randomly generated Nouns with their gender and translation
 * Quizzes are assigned to a user through the QuizController when submitted
 * 
 * @author owenw
 *
 */
@Entity
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="quiz_id")
	private int id;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="question_foreign_id")
	private List<Question> questionList = new LinkedList<Question>();
	@Column
	private int score;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userid")
	private User user;
	
	public Quiz(List<Question> queList) {
		questionList = queList;
		score = 0;
	}
	
	public Quiz() {}
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore(int inc) {
		this.score += inc;
	}
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		String str = "";
		
		for (Question question : questionList) {
			str += question.toString();
		}
		
		return str;
	}
	
}
