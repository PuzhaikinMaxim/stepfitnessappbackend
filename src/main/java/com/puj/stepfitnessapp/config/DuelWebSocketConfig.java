package com.puj.stepfitnessapp.config;

import com.puj.stepfitnessapp.duel.DuelService;
import com.puj.stepfitnessapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class DuelWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final DuelService duelService;

    private final UserService userService;

    @Autowired
    public DuelWebSocketConfig(DuelService duelService, UserService userService) {
        this.duelService = duelService;
        this.userService = userService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/api");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws");
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
                if (sha.getCommand() == StompCommand.DISCONNECT) {
                    var token = (UsernamePasswordAuthenticationToken) sha.getHeader("simpUser");
                    assert token != null;
                    var userId = userService.getUserIdByUsername(token.getPrincipal().toString());
                    duelService.removeFromQueue(userId);
                    System.out.println("conn closed");
                }
            }
        });
    }
}
