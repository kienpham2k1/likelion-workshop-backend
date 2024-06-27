package org.example.likelion.dto.mapper;

import org.example.likelion.dto.request.VoucherRequest;
import org.example.likelion.dto.response.VoucherResponse;
import org.example.likelion.model.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IVoucherMapper {
    IVoucherMapper INSTANCE = Mappers.getMapper(IVoucherMapper.class);

    VoucherResponse toDtoResponse(Voucher entity);

    Voucher toEntity(VoucherRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromEntity(Voucher from, @MappingTarget Voucher to);
}
