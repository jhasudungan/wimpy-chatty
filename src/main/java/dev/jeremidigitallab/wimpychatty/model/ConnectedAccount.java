package dev.jeremidigitallab.wimpychatty.model;

import lombok.Data;

@Data
public class ConnectedAccount {
	
	private String connectionId;
	private String connectionDate;
	private String accountId;
	private String accountDisplayName;
	private String connectionStatus;
	
}
