package dev.jeremidigitallab.wimpychatty.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.jeremidigitallab.wimpychatty.entity.Account;
import dev.jeremidigitallab.wimpychatty.repository.AccountRepository;


public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
	    Optional<Account> account = accountRepository.findByAccountName(username);
		
	    if (account.isEmpty()) {
	    	throw new UsernameNotFoundException(username+" is not found");
	    }
	    	    
		return new CustomUserDetail(account.get());
	}

}
