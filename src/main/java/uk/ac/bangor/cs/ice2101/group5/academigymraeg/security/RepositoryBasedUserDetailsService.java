package uk.ac.bangor.cs.ice2101.group5.academigymraeg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.UserRepository;

/**
 * database based UserDetailsService for authentication and security
 * 
 * @author owenw
 *
 */
@Component
public class RepositoryBasedUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}

}
