package org.example.likelion.service.websocket;


import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.RoomChat;
import org.example.likelion.model.User;
import org.example.likelion.repository.RoomChatRepository;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RoomChatServiceImpl implements RoomChatService {
    private final RoomChatRepository roomChatRepository;
    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    @Override
    public Page<RoomChat> gets(Pageable pageable) {
        return roomChatRepository.findAll(pageable);
    }

    @Override
    public RoomChat get(String roomChatId) {
        return roomChatRepository.findById(roomChatId).orElseThrow();
    }

    @Override
    public void create(RoomChat roomChat) {
        roomChatRepository.save(roomChat);
    }

    @Override
    public RoomChat getRoomChatUser() {
        User user = authenticationService.getCurrUser().orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return roomChatRepository.findByUserId(user.getId()).orElse(createRoomChat());
    }

    public RoomChat createRoomChat() {
        User user = authenticationService.getCurrUser().orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        RoomChat roomChat = RoomChat
                .builder()
                .id(user.getId())
                .createdDate(LocalDate.now())
                .build();
        return roomChatRepository.save(roomChat);
    }
}
