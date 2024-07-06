package org.example.likelion.service.websocket;


import org.example.likelion.model.RoomChat;
import org.springframework.data.domain.Page;

public interface RoomChatService {
    Page<RoomChat> gets();
    void create();

}
