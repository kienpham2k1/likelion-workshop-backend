package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    IProductMapper INSTANCE = Mappers.getMapper(IProductMapper.class);

    ProductResponse toDtoResponse(Product entity);

    Product toEntity(ProductRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromEntity(Product from, @MappingTarget Product to);
}
