package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IOrderMapper;
import org.example.likelion.dto.request.OrderRequest;
import org.example.likelion.dto.response.OrderResponse;
import org.example.likelion.enums.OrderStatus;
import org.example.likelion.model.Order;
import org.example.likelion.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "Order Resource")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get Order List")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getProducts() {
        return orderService.gets().stream().map(IOrderMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Order List Filter")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> getProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return orderService.gets(pageable).map(IOrderMapper.INSTANCE::toDtoResponse);
    }

    @Operation(summary = "Get Order by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getCategory(@PathVariable String id) {
        return IOrderMapper.INSTANCE.toDtoResponse(orderService.get(id));
    }

    @Operation(summary = "Create Order")
    @PostMapping("/create")
    public Order create(@RequestBody @Valid OrderRequest request) {
        return orderService.create(IOrderMapper.INSTANCE.toEntity(request));
    }

    @Operation(summary = "Update Order")
    @PutMapping("/update-status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable String id, @RequestBody @Valid OrderStatus status) {
        orderService.updateStatus(id, status);
    }

    @Operation(summary = "Update Payment")
    @PutMapping("/update-payment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePayment(@PathVariable String id) {
        orderService.updatePayment(id);
    }

    @Operation(summary = "Cancel Order")
    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        orderService.cancel(id);
    }

    @Operation(summary = "Export report")
    @GetMapping("/export-report")
    @ResponseStatus(HttpStatus.OK)
    public void exportMonthlyReport(HttpServletResponse response) {
        orderService.exportMonthlyReport(response);
    }
}
