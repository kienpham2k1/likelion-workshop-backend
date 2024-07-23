package org.example.likelion.dto.mapper;

import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IGeminiAIMapper {
    IGeminiAIMapper INSTANCE = Mappers.getMapper(IGeminiAIMapper.class);

    //    @Mapping(ignore = true, target = "candidates.content")
    GeminiAIResponseDTO toDtoResponse(GeminiAIResponse entity);


}
