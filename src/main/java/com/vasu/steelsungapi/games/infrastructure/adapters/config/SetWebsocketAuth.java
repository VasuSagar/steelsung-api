package com.vasu.steelsungapi.games.infrastructure.adapters.config;

import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE+99)
public class SetWebsocketAuth implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtService jwtService;
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String accessToken = authorization.get(0).split(" ")[1];
                    String uname=null;
                    try {
                        uname = jwtService.getUserNameFromJwtToken(accessToken);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        uname, null, Collections.singleton((GrantedAuthority) () -> "ADMIN"));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        accessor.setUser(authToken);
                    } catch (Exception e) {
                        //TODO: Currently it sets Authority as USER for every exception. Make this logic structure & exclude non trivial exceptions
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        uname, null, Collections.singleton((GrantedAuthority) () -> "USER"));
                        accessor.setUser(authToken);
                    }
                }
                return message;
            }
        });
    }
}
