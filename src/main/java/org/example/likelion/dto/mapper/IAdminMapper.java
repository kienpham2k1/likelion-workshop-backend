package org.example.likelion.dto.mapper;

import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.request.AdminRequest;
import org.example.likelion.dto.response.AdminResponse;
import org.example.likelion.model.Admin;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAdminMapper {
    IAdminMapper INSTANCE = Mappers.getMapper(IAdminMapper.class);

    Admin toEntity(AdminResponse adminResponse);

    AdminResponse toDto(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(AdminResponse adminResponse, @MappingTarget Admin admin);

    Admin toEntity(AdminRequest adminRequest);

    AdminRequest toDto1(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(AdminRequest adminRequest, @MappingTarget Admin admin);

    Admin toEntity(UserDetailsImpl userDetails);
}