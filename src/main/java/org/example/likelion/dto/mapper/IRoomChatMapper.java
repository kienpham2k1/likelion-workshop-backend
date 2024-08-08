package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.RoomChatRequest;
import org.example.likelion.dto.response.RoomChatResponse;
import org.example.likelion.model.RoomChat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IRoomChatMapper {
    IRoomChatMapper INSTANCE = Mappers.getMapper(IRoomChatMapper.class);

    RoomChat toEntity(RoomChatRequest messageRequest);

    RoomChat toEntity(RoomChatResponse messageResponse);

    RoomChatResponse toDtoResponse(RoomChat message);
}
