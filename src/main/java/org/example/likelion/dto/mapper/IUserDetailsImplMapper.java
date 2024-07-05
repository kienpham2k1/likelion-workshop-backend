package org.example.likelion.dto.mapper;

import org.example.likelion.dto.auth.Account;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserDetailsImplMapper {
    IUserDetailsImplMapper INSTANCE = Mappers.getMapper(IUserDetailsImplMapper.class);

    UserDetailsImpl toEntity(Account account);

    Account toDto(UserDetailsImpl userDetailsImpl);
}
