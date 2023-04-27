package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class RegistrationRequest {

	private String accountName;
	private String accountDisplayName;
	private String password;
	
}
