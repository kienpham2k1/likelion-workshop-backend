package org.example.likelion.service.websocket;


import org.example.likelion.model.RoomChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomChatService {
    Page<RoomChat> gets(Pageable pageable);

    RoomChat get(String roomChatId);

    void create(RoomChat roomChat);

    RoomChat getRoomChatUser();
}
