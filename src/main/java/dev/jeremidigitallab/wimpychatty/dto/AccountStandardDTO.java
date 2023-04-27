package dev.jeremidigitallab.wimpychatty.dto;

import java.util.Date;

import dev.jeremidigitallab.wimpychatty.entity.Account;
import lombok.Data;

@Data
public class AccountStandardDTO {

    private String accountId;
    private String accountName;
    private String accountDisplayName;
    private Date createdDate;
    private Date lastModified;
    
	public AccountStandardDTO(Account account) {
		
		super();
		this.accountId = account.getAccountId();
		this.accountName = account.getAccountName();
		this.accountDisplayName = account.getAccountDisplayName();
		this.createdDate = account.getCreatedDate();
		this.lastModified = account.getLastModified();
		
	}
	
}
