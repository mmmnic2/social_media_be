package com.firstversion.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // đăng ký một websocket endpoint mà các máy khác sẽ sử dụng để kết nối với máy chủ websocket.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Định nghĩa prefix cho các tin nhắn đến từ client
        registry.setApplicationDestinationPrefixes("/app");
        // Kích hoạt một SimpleBroker với các topic
        registry.enableSimpleBroker("/topic");
    }
}
