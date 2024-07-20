package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IRoomChatMapper;
import org.example.likelion.dto.response.RoomChatResponse;
import org.example.likelion.model.RoomChat;
import org.example.likelion.service.websocket.RoomChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roomChat")
public class RoomChatController {
    private final RoomChatService roomChatService;

    @GetMapping("/admin/gets")
    @ResponseStatus(HttpStatus.OK)
    public Page<RoomChatResponse> getMessageByRoomId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "50") Integer pageSize,
                                                     @RequestParam(defaultValue = "asc") String sortDirection,
                                                     @RequestParam(defaultValue = "createdDate") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return roomChatService.gets(pageable).map(IRoomChatMapper.INSTANCE::toDtoResponse);
    }

    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoomChat(@PathVariable String userId) {
        roomChatService.createRoomChatWithUser(userId, new RoomChat());
    }

    @GetMapping("/user/getRoomChat")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomChatResponse getRoomChatUser() {
        return IRoomChatMapper.INSTANCE.toDtoResponse(roomChatService.getRoomChatUser());
    }
}
