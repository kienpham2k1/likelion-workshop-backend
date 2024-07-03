package org.example.likelion.service.websocket;

import org.example.likelion.model.Message;
import org.springframework.data.domain.Page;

public interface MessageService {
    Page<Message> gets(String roomId);

    Message create(String roomId, Message message);
}
