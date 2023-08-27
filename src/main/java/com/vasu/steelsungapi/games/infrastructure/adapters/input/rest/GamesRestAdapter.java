package com.vasu.steelsungapi.games.infrastructure.adapters.input.rest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GamesRestAdapter {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void broadcastNews(String message) {
        this.simpMessagingTemplate.convertAndSend("/topic/receive-chat", message);
    }
}
