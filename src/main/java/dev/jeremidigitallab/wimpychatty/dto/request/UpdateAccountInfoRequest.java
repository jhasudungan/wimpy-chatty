package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class UpdateAccountInfoRequest {

	private String accountId;
	private String accountName;
	private String accountDisplayName;
	private String accountLoginPassword;
	
}
