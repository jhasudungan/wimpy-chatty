package dev.jeremidigitallab.wimpychatty.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.jeremidigitallab.wimpychatty.entity.AccountFriend;

public interface AccountFriendRepository extends PagingAndSortingRepository<AccountFriend, String> {

	Page<AccountFriend> findByTargetId(String targetId,Pageable pageable);
	
	Page<AccountFriend> findBySourceId(String sourceId,Pageable pageable);
}
