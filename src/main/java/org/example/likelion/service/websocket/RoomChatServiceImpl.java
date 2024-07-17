package org.example.likelion.service.websocket;


import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
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
        UserDetailsImpl userDetails = authenticationService.getCurrentUser().get();
        if (userDetails.getRole().equals(Role.ADMIN)) return roomChatRepository.findAll(pageable);
        else {
            createRoomChat();
            return roomChatRepository.findRoomChatByUserId(userDetails.getId(), pageable);
        }
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

    public void createRoomChatWithUser(String userId, RoomChat roomChat) {
        roomChat.setUser(userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND)));
        roomChat.setCreatedDate(LocalDate.now());
        roomChatRepository.save(roomChat);
    }

    @Override
    public RoomChat getRoomChatUser() {
        User user = authenticationService.getCurrUser().orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return roomChatRepository.findByUserId(user.getId()).orElseGet(this::createRoomChat);
    }

    public RoomChat createRoomChat() {
        User user = authenticationService.getCurrUser().orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        RoomChat roomChat = RoomChat
                .builder()
                .createdDate(LocalDate.now())
                .user(userRepository.findById(user.getId()).orElseThrow())
                .build();
        return roomChatRepository.save(roomChat);
    }
}
