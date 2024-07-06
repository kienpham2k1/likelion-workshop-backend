package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IMessageMapper;
import org.example.likelion.dto.request.MessageRequest;
import org.example.likelion.dto.response.MessageResponse;
import org.example.likelion.service.websocket.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/getMessage/{roomChatId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageResponse> getMessageByRoomId(@PathVariable(name = "roomChatId") String roomChatId,
                                                    @RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "50") Integer pageSize,
                                                    @RequestParam(defaultValue = "asc") String sortDirection,
                                                    @RequestParam(defaultValue = "createdDate") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return messageService.gets(roomChatId, pageable).map(IMessageMapper.INSTANCE::toDtoResponse);
    }

    @PostMapping("/create/{roomdId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@PathVariable String roomdId, @RequestBody MessageRequest messageRequest) {
        messageService.create(roomdId, IMessageMapper.INSTANCE.toEntity(messageRequest));
    }
}
