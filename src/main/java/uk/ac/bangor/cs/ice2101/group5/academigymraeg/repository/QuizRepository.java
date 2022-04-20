package uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.quiz.Quiz;
import uk.ac.bangor.cs.ice2101.group5.academigymraeg.web.NounRequest;

/**
 * Repository to handle CRUD methods for Quiz objects
 * 
 * @author owenw
 *
 */
@Repository
public interface QuizRepository extends CrudRepository<Quiz, Integer> {

	List<Quiz> findByUserUserID(long userID);
	
	Quiz findByIdAndUserUserID(int id, long userID);
	
	/**
	 * Used to delete any quizzes that haven't been completed
	 * @return uncomplete quizzes
	 */
	List<Quiz> findByUserUserIDIsNull();

}
