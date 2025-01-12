package com.OhsunDosun._config;

import com.OhsunDosun.Handler.ConversationWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ConversationWebSocketHandler conversationWebSocketHandler;

    public WebSocketConfig(ConversationWebSocketHandler conversationWebSocketHandler) {
        this.conversationWebSocketHandler = conversationWebSocketHandler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(conversationWebSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
