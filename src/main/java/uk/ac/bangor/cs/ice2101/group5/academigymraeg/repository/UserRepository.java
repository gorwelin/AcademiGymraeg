package uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository;


import org.springframework.data.repository.CrudRepository;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.security.User;

/**
 * User repository to manage users through CRUD requests from CrudRepository
 * 
 * @author owenw
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
}
