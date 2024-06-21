package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.OrderDetailRequest;
import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.response.OrderDetailResponse;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.model.OrderDetail;
import org.example.likelion.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IOrderDetailMapper {
    IOrderDetailMapper INSTANCE = Mappers.getMapper(IOrderDetailMapper.class);

    OrderDetailResponse toDtoResponse(OrderDetail entity);

    OrderDetail toEntity(OrderDetailRequest request);
}
