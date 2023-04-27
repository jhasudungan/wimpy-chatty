package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {

	private String accountId;
	private String currentPassword;
	private String newPassword;
	private String repeatedNewPassword;
	
}
