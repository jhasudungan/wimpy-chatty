package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class GetRoomChatInformationRequest {

	private String accountId;
	private String connectedId;
}
