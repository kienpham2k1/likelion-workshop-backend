package org.example.likelion.service.websocket;

import org.example.likelion.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<Message> gets(String roomId);

    Message create(String roomId, Message message);

    Message get(String messageId);

    Page<Message> gets(String roomChatId, Pageable pageable);
}
