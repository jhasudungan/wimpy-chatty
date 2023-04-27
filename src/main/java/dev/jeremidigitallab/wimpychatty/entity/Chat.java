package dev.jeremidigitallab.wimpychatty.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import dev.jeremidigitallab.wimpychatty.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Chat {

	@Id
	private String chatId;
	private String connectedId;
	private String sender;
	private String receviver;
	private String text;
	
	public Chat(ChatMessage chatMessage) {
		super();
		this.chatId = UUID.randomUUID().toString();
		this.connectedId = chatMessage.getConnectionId();
		this.sender = chatMessage.getSender();
		this.receviver = chatMessage.getReceiver();
		this.text = chatMessage.getMessageText();
	}


}
