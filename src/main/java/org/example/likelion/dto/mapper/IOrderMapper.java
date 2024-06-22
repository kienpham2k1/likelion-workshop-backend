package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.OrderRequest;
import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.response.OrderResponse;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.model.Order;
import org.example.likelion.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    IOrderMapper INSTANCE = Mappers.getMapper(IOrderMapper.class);

    OrderResponse toDtoResponse(Order entity);

    Order toEntity(OrderRequest request);
}
