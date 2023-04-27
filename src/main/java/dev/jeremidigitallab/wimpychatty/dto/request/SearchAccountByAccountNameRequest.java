package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data 
public class SearchAccountByAccountNameRequest {

	private String accountName;
	private String requestorAccountId;
	
}
