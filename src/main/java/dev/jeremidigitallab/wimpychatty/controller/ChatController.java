package dev.jeremidigitallab.wimpychatty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import dev.jeremidigitallab.wimpychatty.entity.Chat;
import dev.jeremidigitallab.wimpychatty.model.ChatMessage;
import dev.jeremidigitallab.wimpychatty.repository.ChatRepository;


@Controller
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    ChatRepository chatRepository;
    
    @MessageMapping("/personal")
    public void processPersonalMessage(@Payload ChatMessage chatMessage) {
    	
    	chatRepository.save(new Chat(chatMessage));
    	
        simpMessagingTemplate.convertAndSend("/client/" + chatMessage.getConnectionId() + "/queue", chatMessage);
    }
}
