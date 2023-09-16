package com.vasu.steelsungapi.games.infrastructure.adapters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class ConfigureInboundAndOriginConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
        @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/app/coinflip").hasAuthority("ADMIN")
                .simpSubscribeDestMatchers("/topic/coinflip").permitAll()
                .simpSubscribeDestMatchers("/user/**").hasAnyAuthority("ADMIN")
                .simpTypeMatchers(SimpMessageType.CONNECT).permitAll()
                .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
