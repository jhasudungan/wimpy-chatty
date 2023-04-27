package dev.jeremidigitallab.wimpychatty.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import dev.jeremidigitallab.wimpychatty.entity.Account;
import dev.jeremidigitallab.wimpychatty.entity.AccountFriend;

@DataJpaTest
@RunWith(SpringRunner.class)
class TestAccountRepository {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountFriendRepository accountFriendRepository;

	
	@BeforeEach
	void createTestData() {
		
		Account account = new Account();
		account.setAccountId("1de4b603-1d73-4587-91fa-9a89b22f8c9c");
		account.setAccountName("account1");
		account.setAccountLoginPassword("12345");
		account.setAccountDisplayName("Account 1");
		account.setCreatedDate(new Date());
		account.setLastModified(new Date());
		
		Account account2 = new Account();
		account2.setAccountId("1de4b603-1d73-4587-91fa-9a89b22f8c9d");
		account2.setAccountName("account2");
		account2.setAccountLoginPassword("12345");
		account2.setAccountDisplayName("Account 2");
		account2.setCreatedDate(new Date());
		account2.setLastModified(new Date());
		
		Account account3 = new Account();
		account3.setAccountId("1de4b603-1d73-4587-91fa-9a89b22f8c9e");
		account3.setAccountName("account3");
		account3.setAccountLoginPassword("12345");
		account3.setAccountDisplayName("Account 3");
		account3.setCreatedDate(new Date());
		account3.setLastModified(new Date());
		
		accountRepository.save(account);
		accountRepository.save(account2);
		accountRepository.save(account3);
		
		AccountFriend newConnected = new AccountFriend();
		newConnected.setConnectedId("ca6de5dd-e2b9-4f1f-a1b0-eeb2320ead29");
		newConnected.setTargetId(account.getAccountId());
		newConnected.setSourceId(account2.getAccountId());
		newConnected.setCreatedDate(new Date());
		newConnected.setLastModified(new Date());
		
		AccountFriend newConnected2 = new AccountFriend();
		newConnected2.setConnectedId("ca6de5dd-e2b9-4f1f-a1b0-eeb2320ead30");
		newConnected2.setTargetId(account3.getAccountId());
		newConnected2.setSourceId(account.getAccountId());
		newConnected2.setCreatedDate(new Date());
		newConnected2.setLastModified(new Date());
		
		accountFriendRepository.save(newConnected);
		accountFriendRepository.save(newConnected2);
	}
	
	@Test
	void testGetAccountFriendByTargetId_Page1() {

        Pageable pageable = PageRequest.of(0,5);
        Page<AccountFriend> target = accountFriendRepository.findByTargetId("1de4b603-1d73-4587-91fa-9a89b22f8c9c", pageable);
        
        assertEquals(1, target.get().count());;
       
	}
	
	@Test
	void testGetAccountFriendBySourceId_Page1() {

        Pageable pageable = PageRequest.of(0,5);
        Page<AccountFriend> target = accountFriendRepository.findBySourceId("1de4b603-1d73-4587-91fa-9a89b22f8c9c", pageable);
        
        assertEquals(1, target.get().count());;
       
	}
	
	@Test
	void testSearchAccountByAccountName() {
		
		List<Account> accounts = accountRepository.findByAccountNameLike("%count1%");
		
		assertEquals(1,accounts.size());
		assertEquals("1de4b603-1d73-4587-91fa-9a89b22f8c9c",accounts.get(0).getAccountId());
		
	}
	
}
