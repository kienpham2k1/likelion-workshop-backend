package org.example.likelion.service.websocket;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Message;
import org.example.likelion.repository.MessageRepository;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Message get(String messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.MESSAGE_NOT_FOUND));
    }

    @Override
    public Message create(String roomId, Message message) {
        message.setRoomId(roomId);
        message.setCreatedDate(LocalDate.now());
        return messageRepository.save(message);
    }
}
