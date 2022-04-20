package uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.web.NounRequest;

/**
 * Repository to handle CRUD requests for Nouns using CrudRepository interface
 * 
 * @author johnn
 * @author owenw
 *
 */
@Repository
public interface NounRequestRepository extends CrudRepository<NounRequest, Integer> {

	List<NounRequest> findAllByOrderByPrimaryKeyDesc();

	List<NounRequest> findByNounGenders(String nounGenders);

	List<NounRequest> findAllByOrderByEnglishNoun();

	List<NounRequest> findAllByOrderByWelshNoun();

	List<NounRequest> findByEnglishNoun(String value);

	List<NounRequest> findByWelshNoun(String value);

}
