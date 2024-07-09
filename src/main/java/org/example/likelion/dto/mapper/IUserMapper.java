package org.example.likelion.dto.mapper;

import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.UserResponse;
import org.example.likelion.model.Admin;
import org.example.likelion.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserResponse toDtoResponse(User user);
    @Mapping(target = "verify", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "addressLine", ignore = true)
    UserResponse toDtoResponse(Admin admin);
//    UserRegisterResponse toDtoRegisterResponse(UserDetailsImpl user, JwtResponse jwtResponse);

    User toEntity(UserRegisterRequest request);

    User toEntity(UserDetailsImpl userDetails);
}
