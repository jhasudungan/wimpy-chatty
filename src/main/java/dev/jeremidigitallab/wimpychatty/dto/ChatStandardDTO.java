package dev.jeremidigitallab.wimpychatty.dto;

import dev.jeremidigitallab.wimpychatty.entity.Chat;
import lombok.Data;

@Data
public class ChatStandardDTO {

	private String chatId;
	private String connectedId;
	private String sender;
	private String receviver;
	private String text;
	
	public ChatStandardDTO(Chat chat) {
		super();
		this.chatId = chat.getChatId();
		this.connectedId = chat.getConnectedId();
		this.sender = chat.getSender();
		this.receviver = chat.getReceviver();
		this.text = chat.getText();
	}
	
	
	
}
