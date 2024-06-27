package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IVoucherMapper;
import org.example.likelion.dto.request.VoucherRequest;
import org.example.likelion.dto.response.VoucherResponse;
import org.example.likelion.repository.VoucherRepository;
import org.example.likelion.service.VoucherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/voucher")
@Tag(name = "Voucher Resource")
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;

    @Operation(summary = "Get Voucher List")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherResponse> getVouchers() {
        return voucherService.gets().stream().map(IVoucherMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Voucher List Filter")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<VoucherResponse> getProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return voucherService.gets(pageable).map(IVoucherMapper.INSTANCE::toDtoResponse);
    }

    @Operation(summary = "Get Voucher by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherResponse getVoucher(@PathVariable String id) {
        return IVoucherMapper.INSTANCE.toDtoResponse(voucherService.get(id));
    }

    @Operation(summary = "Create Voucher")
    @PostMapping("/create")
    public void create(@RequestBody @Valid VoucherRequest request) {
        voucherService.create(IVoucherMapper.INSTANCE.toEntity(request));
    }

    @Operation(summary = "Delete Voucher")
    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        voucherService.delete(id);
    }

    @Operation(summary = "Update Voucher")
    @PutMapping("/updateStatus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable String id) {
        voucherService.updateStatus(id);
    }
}
