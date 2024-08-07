package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.MessageRequest;
import org.example.likelion.dto.response.MessageResponse;
import org.example.likelion.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IMessageMapper {
    IMessageMapper INSTANCE = Mappers.getMapper(IMessageMapper.class);

    @Mapping(ignore = true, target = "attachment")
    Message toEntity(MessageRequest messageRequest);

    Message toEntity(MessageResponse messageResponse);

    MessageResponse toDtoResponse(Message message);
}
