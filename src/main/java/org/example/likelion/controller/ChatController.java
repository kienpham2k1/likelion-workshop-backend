 package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.request.MessageRequest;
import org.example.likelion.dto.request.infobip.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("chat/{roomId}")
    public void processMessage(
            @DestinationVariable String roomId,
            @Payload MessageRequest messageRequest) {

        messagingTemplate.convertAndSend(
                "/chat/app" + roomId,
                "");
    }
}
