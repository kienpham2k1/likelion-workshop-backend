package org.example.likelion.service.websocket;

import org.example.likelion.model.Room;
import org.springframework.data.domain.Page;

public interface RoomChatService {
    Page<Room> gets();
    void create();

}
