package dev.jeremidigitallab.wimpychatty.model;

import lombok.Data;

@Data
public class ChatMessage {

    private String sender;
    private String receiver;
    private String connectionId;
    private String messageText;

}
