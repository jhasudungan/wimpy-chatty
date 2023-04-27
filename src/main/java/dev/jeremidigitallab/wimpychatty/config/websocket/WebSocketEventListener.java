package dev.jeremidigitallab.wimpychatty.config.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import lombok.extern.java.Log;

@Log
@Component
public class WebSocketEventListener {


    /*
     * After client estabilish a websocket connection
     * A message-based operation will be conducted.
     * 
     * Event listener will listen for every event send by client
     */

    @EventListener
    public void handleClientConnectionEvent(SessionConnectedEvent event) {
        log.info("a client connected to web socket");
    }

}
