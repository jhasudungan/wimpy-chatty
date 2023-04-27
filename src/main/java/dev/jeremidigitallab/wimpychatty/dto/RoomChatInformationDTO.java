package dev.jeremidigitallab.wimpychatty.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoomChatInformationDTO {

	private String sender;
	private String senderName;
	private String receiver;
	private String receiverDisplayName;
	private String receiverAccountName;
	private String connectedId;
	
	private List<ChatStandardDTO> savedChats;
}
