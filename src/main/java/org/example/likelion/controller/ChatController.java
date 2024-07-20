package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.mapper.IMessageMapper;
import org.example.likelion.dto.request.MessageRequest;
import org.example.likelion.dto.response.MessageResponse;
import org.example.likelion.exception.errorModel.ErrorMessageEntity;
import org.example.likelion.model.Message;
import org.example.likelion.service.websocket.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;

    @MessageMapping("/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId,
                            @Payload MessageRequest message,
                            SimpMessageHeaderAccessor headerAccessor) {
        AbstractAuthenticationToken authenticationToken = (AbstractAuthenticationToken) headerAccessor.getUser();
        UserDetailsImpl userDetails = (UserDetailsImpl) Objects.requireNonNull(authenticationToken).getPrincipal();
        Message e = IMessageMapper.INSTANCE.toEntity(message);
        if (userDetails.getRole() == Role.USER) {
            e.setUserId(userDetails.getId());
        }
        var msi = messageService.create(roomId, e);
        MessageResponse rp = IMessageMapper.INSTANCE.toDtoResponse(messageService.get(msi.getId()));
        messagingTemplate.convertAndSend("/topic/" + roomId, rp);

    }

    @MessageExceptionHandler
    public void handleException(@DestinationVariable String roomId,
                                Exception e) {
        ErrorMessageEntity errorMessageEntity = ErrorMessageEntity
                .builder()

                .message(e.getMessage())
                .build();
        messagingTemplate.convertAndSend("/topic/" + roomId, errorMessageEntity);
    }
}
