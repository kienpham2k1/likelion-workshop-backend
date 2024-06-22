package org.example.likelion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IOrderMapper;
import org.example.likelion.dto.request.OrderRequest;
import org.example.likelion.dto.response.OrderResponse;
import org.example.likelion.service.IOrderService;
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
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getProducts() {
        return orderService.gets().stream().map(IOrderMapper.INSTANCE::toDtoResponse).toList();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> getProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return orderService.gets(pageable).map(IOrderMapper.INSTANCE::toDtoResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getCategory(@PathVariable String id) {
        return IOrderMapper.INSTANCE.toDtoResponse(orderService.get(id));
    }

    @PostMapping("/create")

    public void create(@RequestBody @Valid OrderRequest request) {
//        orderService.create(IOrderMapper.INSTANCE.toEntity(request));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid OrderRequest request) {
        orderService.update(id, IOrderMapper.INSTANCE.toEntity(request));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        orderService.delete(id);
    }
}
