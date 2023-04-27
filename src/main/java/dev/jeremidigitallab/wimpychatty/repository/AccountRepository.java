package dev.jeremidigitallab.wimpychatty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.jeremidigitallab.wimpychatty.entity.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {

	List<Account> findByAccountNameLike(String accountName);
	
	Optional<Account> findByAccountName(String accountName);
	
}
