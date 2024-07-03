package org.example.likelion.service.websocket;

import lombok.RequiredArgsConstructor;
import org.example.likelion.model.Message;
import org.example.likelion.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public Page<Message> gets(String roomId) {
        return null;
    }

    @Override
    public Message create(String roomId, Message message) {
        return messageRepository.save(message);
    }
}
