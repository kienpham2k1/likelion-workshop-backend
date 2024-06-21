package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.CategoryRequest;
import org.example.likelion.dto.response.CategoryResponse;
import org.example.likelion.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);
    CategoryResponse toDtoResponse(Category entity);
    Category toEntity(CategoryRequest request);
}
