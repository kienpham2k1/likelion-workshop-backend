package org.example.likelion.service.websocket;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Message;
import org.example.likelion.repository.MessageRepository;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private final AuthenticationService authenticationService;

    @Override
    public List<Message> gets(String roomId) {
        return messageRepository.findAllByRoomId(roomId);
    }

    @Override
    public Page<Message> gets(String roomChatId, Pageable pageable) {
        return messageRepository.findAllByRoomId(roomChatId, pageable);
    }

    @Override
    public Message create(String roomId, Message message) {
        UserDetailsImpl userDetails = authenticationService.getCurrentUser().orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        message.setRoomId(roomId);
        message.setUserId(userDetails.getId());
        return messageRepository.save(message);
    }
}
