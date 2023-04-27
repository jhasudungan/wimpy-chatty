package dev.jeremidigitallab.wimpychatty.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jeremidigitallab.wimpychatty.dto.AccountFriendStandardDTO;
import dev.jeremidigitallab.wimpychatty.dto.AccountStandardDTO;
import dev.jeremidigitallab.wimpychatty.dto.ChatStandardDTO;
import dev.jeremidigitallab.wimpychatty.dto.PageInformation;
import dev.jeremidigitallab.wimpychatty.dto.ResponseWithData;
import dev.jeremidigitallab.wimpychatty.dto.RoomChatInformationDTO;
import dev.jeremidigitallab.wimpychatty.dto.request.AddNewFriendRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetAccountFriendInformationRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetAccountInfoRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetListOfFriendRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetRoomChatInformationRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.RegistrationRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.SearchAccountByAccountNameRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.UpdateAccountInfoRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.UpdatePasswordRequest;
import dev.jeremidigitallab.wimpychatty.entity.Account;
import dev.jeremidigitallab.wimpychatty.entity.AccountFriend;
import dev.jeremidigitallab.wimpychatty.exception.RegistrationProcessExeption;
import dev.jeremidigitallab.wimpychatty.model.ConnectedAccount;
import dev.jeremidigitallab.wimpychatty.repository.AccountFriendRepository;
import dev.jeremidigitallab.wimpychatty.repository.AccountRepository;
import dev.jeremidigitallab.wimpychatty.repository.ChatRepository;
import dev.jeremidigitallab.wimpychatty.repository.CustomAccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    CustomAccountRepository customAccountRepository;

    @Autowired
    AccountFriendRepository accountFriendRepository;
    
    @Autowired
    ChatRepository chatRepository;
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Value("${pagination.limit}")
	private Integer paginationLimit;
    
    public ResponseWithData<List<ConnectedAccount>> getListOfFriend(GetListOfFriendRequest request) throws SQLException {
        
        if (request.getPage() <= 0) {
        	request.setPage(1);
        }
        
        List<ConnectedAccount> connectedAccounts = customAccountRepository.
        												getAccountFriend(request.getAccountId(), request.getPage());
        
        PageInformation pageInformation = new PageInformation();
        pageInformation.setCurrentPage(request.getPage());
        pageInformation.setNextPage(request.getPage()+1);
        pageInformation.setPreviousPage(request.getPage()-1);
        
        Double temp = Math.ceil(customAccountRepository.countAccountFriend(request.getAccountId()).doubleValue()/paginationLimit.doubleValue());  
        pageInformation.totalPage = temp.intValue();
        		             
        ResponseWithData<List<ConnectedAccount>> responseWithData = new ResponseWithData<>();
        responseWithData.setData(connectedAccounts);
        responseWithData.setPageInformation(pageInformation);
         
        return responseWithData;
    }
    
    public ResponseWithData<List<ConnectedAccount>> searchConnectedAccount(SearchAccountByAccountNameRequest request) throws SQLException {
    	    	
    	ResponseWithData<List<ConnectedAccount>> responseWithData = new ResponseWithData<>();
    	List<ConnectedAccount> connectedAccounts = customAccountRepository.findConnectedAccountByName(request.getRequestorAccountId(), request.getAccountName());
    	
    	responseWithData.setData(connectedAccounts);
    	
    	return responseWithData;
    }
    
    public RoomChatInformationDTO getChatSenderReceiverInformation(GetRoomChatInformationRequest request) {
    	
    	RoomChatInformationDTO roomChatInformation = new RoomChatInformationDTO();
    	
    	roomChatInformation.setSender(request.getAccountId());
    	roomChatInformation.setConnectedId(request.getConnectedId());
    	
    	Optional<Account> findAccountSender = accountRepository.findById(request.getAccountId());
    	
    	if (findAccountSender.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	roomChatInformation.setSenderName(findAccountSender.get().getAccountDisplayName());
    	
    	Optional<AccountFriend> findAccountFriend = accountFriendRepository.findById(request.getConnectedId());
    	
    	if (findAccountFriend.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	if (findAccountFriend.get().getSourceId().equalsIgnoreCase(request.getAccountId())) {
    		
    		Optional<Account> findAccountReceiver = accountRepository.findById(findAccountFriend.get().getTargetId());
        	
        	if (findAccountReceiver.isEmpty()) {
        		throw new ApplicationContextException("Data not found");
        	}
        	
        	roomChatInformation.setReceiver(findAccountReceiver.get().getAccountId());
        	roomChatInformation.setReceiverAccountName(findAccountReceiver.get().getAccountName());
        	roomChatInformation.setReceiverDisplayName(findAccountReceiver.get().getAccountDisplayName());
    		
    	} else {
    		
    		Optional<Account> findAccountReceiver = accountRepository.findById(findAccountFriend.get().getSourceId());
        	
        	if (findAccountReceiver.isEmpty()) {
        		throw new ApplicationContextException("Data not found");
        	}
        	
        	roomChatInformation.setReceiver(findAccountReceiver.get().getAccountId());
        	roomChatInformation.setReceiverAccountName(findAccountReceiver.get().getAccountName());
        	roomChatInformation.setReceiverDisplayName(findAccountReceiver.get().getAccountDisplayName());

    	}
    	
    	roomChatInformation.setSavedChats(new ArrayList<>());
    	
		chatRepository.findByConnectedId(request.getConnectedId())
				.forEach(data -> roomChatInformation.getSavedChats().add(new ChatStandardDTO(data)));    	
    	
    	return roomChatInformation;
    	
    }
    
    public AccountFriendStandardDTO getAccountFriendInformation(GetAccountFriendInformationRequest request) {
    	
    	Optional<AccountFriend> findAccountFriend = accountFriendRepository.findById(request.getConnectedId());
    	
    	if (findAccountFriend.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	return new AccountFriendStandardDTO(findAccountFriend.get());
    }
     
    public AccountStandardDTO getAccountInfo(GetAccountInfoRequest request) {
    	
    	Optional<Account> findAccount = accountRepository.findById(request.getAccountId());
    	
    	if (findAccount.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	return new AccountStandardDTO(findAccount.get());
    }
    
    public List<AccountStandardDTO> searchAccountByAccountName(SearchAccountByAccountNameRequest request) throws SQLException {
    
    	List<AccountStandardDTO> result = new ArrayList<>();

    	customAccountRepository
    		.findNotConnectedAccountByName(request.getRequestorAccountId(), request.getAccountName())
    		.forEach(data -> result.add(new AccountStandardDTO(data)));
    	
    	return result;
    }
    
    public void addnewFriend(AddNewFriendRequest request) {
    		
    	AccountFriend accountFriend = new AccountFriend();
    	accountFriend.setConnectedId(UUID.randomUUID().toString());
    	accountFriend.setSourceId(request.getSourceId());
    	accountFriend.setTargetId(request.getTargetId());
    	
    	accountFriendRepository.save(accountFriend);
    }
    
    public void registerNewAccount(RegistrationRequest request) throws RegistrationProcessExeption {
    	
    	Optional<Account> findAccount = accountRepository.findByAccountName(request.getAccountName());
    	
    	if (findAccount.isPresent()) {
    		throw new RegistrationProcessExeption("Account name already used");
    	}
        	
    	Account account = new Account();
    	account.setAccountId(UUID.randomUUID().toString());
    	account.setAccountName(request.getAccountName());
    	account.setAccountDisplayName(request.getAccountDisplayName());
    	account.setAccountLoginPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    	account.setCreatedDate(new Date());
    	account.setLastModified(new Date());
    	
    	accountRepository.save(account);
    	
    }
    
    public void updateAccountInformation(UpdateAccountInfoRequest request) {
    	
    	Optional<Account> findAccount = accountRepository.findById(request.getAccountId());
    	
    	if (findAccount.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	Account account = findAccount.get();
    	
//    	Only Update Display Name for Now
    	account.setAccountDisplayName(request.getAccountDisplayName());
    	account.setLastModified(new Date());
    	
    	accountRepository.save(account);
    }
    
    public void updatePassword(UpdatePasswordRequest request) {
    	
    	Optional<Account> findAccount = accountRepository.findById(request.getAccountId());
    	
    	if (findAccount.isEmpty()) {
    		throw new ApplicationContextException("Data not found");
    	}
    	
    	if (!request.getNewPassword().equalsIgnoreCase(request.getRepeatedNewPassword())) {
    		throw new ApplicationContextException("New Password Not Matched");
    	}
    	
    	boolean matched = bCryptPasswordEncoder.matches(request.getCurrentPassword(), findAccount.get().getAccountLoginPassword());
    	
    	if (matched) {
    		
    		Account account = findAccount.get();
    		account.setAccountLoginPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
    		accountRepository.save(account);
    	
    	} else {
    		throw new ApplicationContextException("Current Password is Not Matched");
    	}
    }
    
    
}
