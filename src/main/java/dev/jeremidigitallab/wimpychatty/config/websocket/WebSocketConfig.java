package dev.jeremidigitallab.wimpychatty.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Client estabilish a websocket connection to "/ws"
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /**
         * User can subscribe and listen on subscribe to "/client/{connectionId}" so we need
         * to registry it on simple broker and user destination
         */
        registry.enableSimpleBroker("/client");

        /**
         * After a client estabilish connection ("/ws")
         * client can send data through "/app/personal"
         */
        registry.setApplicationDestinationPrefixes("/app");

        /**
         * User can subscribe and listen on subscribe to "/client/{connectionId}"
         */
        registry.setUserDestinationPrefix("/client");

    }

}
