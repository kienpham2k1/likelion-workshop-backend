package org.example.likelion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                assert accessor != null;
//                accessor.setUser(null);
//                if (accessor.getCommand() == StompCommand.SEND
//                        || accessor.getCommand() == StompCommand.MESSAGE
//                        || accessor.getCommand() == StompCommand.SUBSCRIBE) {
//                    final String accessToken = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
//                    jwtService.extractUserId(accessToken);
//                    if (accessToken != null) {
//                        String userId = jwtService.extractUserId(accessToken);
//                        accessor.setUser(() -> userId);
//                    } else throw new VerificationException("Invalid token");
//                }
//                return message;
//            }
//        });
//    }
}
