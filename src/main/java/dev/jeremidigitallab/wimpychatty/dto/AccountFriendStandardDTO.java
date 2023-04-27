package dev.jeremidigitallab.wimpychatty.dto;

import java.util.Date;

import dev.jeremidigitallab.wimpychatty.entity.AccountFriend;
import lombok.Data;

@Data
public class AccountFriendStandardDTO {

    private String connectedId;

    private String sourceId;

    private String targetId;

    private Date createdDate;

    private Date lastModified;

	public AccountFriendStandardDTO(AccountFriend accountFriend) {
		super();
		this.connectedId = accountFriend.getConnectedId();
		this.sourceId = accountFriend.getSourceId();
		this.targetId = accountFriend.getTargetId();
		this.createdDate = accountFriend.getCreatedDate();
		this.lastModified = accountFriend.getLastModified();
	}
    
    
}
