package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IOrderDetailMapper;
import org.example.likelion.dto.response.OrderDetailResponse;
import org.example.likelion.service.OrderDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-detail")
@Tag(name = "Order Detail Resource")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Operation(summary = "Get All Order Detail")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetailResponse> getProducts(@RequestParam String orderId) {
        return orderDetailService.gets(orderId).stream().map(IOrderDetailMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Order Detail Filter")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDetailResponse> getProducts(
            @RequestParam String orderId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return orderDetailService.gets(orderId, pageable).map(IOrderDetailMapper.INSTANCE::toDtoResponse);
    }

    @Operation(summary = "Get Order Detail by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse getCategory(@PathVariable String id) {
        return IOrderDetailMapper.INSTANCE.toDtoResponse(orderDetailService.get(id));
    }
}
