package org.example.likelion.dto.mapper;


import org.example.likelion.dto.request.PromotionRequest;
import org.example.likelion.dto.response.PromotionResponse;
import org.example.likelion.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IPromotionMapper {
    IPromotionMapper INSTANCE = Mappers.getMapper(IPromotionMapper.class);

    PromotionResponse toDtoResponse(Promotion entity);

    Promotion toEntity(PromotionRequest request);
}
