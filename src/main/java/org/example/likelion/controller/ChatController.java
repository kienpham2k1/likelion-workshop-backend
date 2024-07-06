package org.example.likelion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IMessageMapper;
import org.example.likelion.dto.request.MessageRequest;
import org.example.likelion.dto.response.MessageResponse;
import org.example.likelion.model.Message;
import org.example.likelion.service.websocket.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;

    @MessageMapping("/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest message) {
        Message e = IMessageMapper.INSTANCE.toEntity(message);
        MessageResponse rp = IMessageMapper.INSTANCE.toDtoResponse(messageService.create(roomId, e));
        messagingTemplate.convertAndSend("/topic/" + roomId, rp);
    }
}
