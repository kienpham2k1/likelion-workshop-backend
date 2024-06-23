package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.UserResponse;
import org.example.likelion.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserResponse toDtoResponse(User entity);

    User toEntity(UserRegisterRequest request);
}
