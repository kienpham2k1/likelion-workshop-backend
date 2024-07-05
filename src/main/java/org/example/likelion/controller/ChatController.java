package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.request.MessageRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest chatMessage) {
        messagingTemplate.convertAndSend("/topic/" + roomId, chatMessage);
    }
}
