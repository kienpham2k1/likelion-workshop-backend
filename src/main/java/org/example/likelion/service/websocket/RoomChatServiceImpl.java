package org.example.likelion.service.websocket;

import org.example.likelion.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RoomChatServiceImpl implements RoomChatService {
    @Override
    public Page<Room> gets() {
        return null;
    }

    @Override
    public void create() {

    }
}
