package org.example.likelion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IProductMapper;
import org.example.likelion.dto.mapper.IProductMapper;
import org.example.likelion.dto.request.CategoryRequest;
import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.response.CategoryResponse;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.model.Product;
import org.example.likelion.service.ICategoryService;
import org.example.likelion.service.IProductService;
import org.example.likelion.service.implService.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return productService.gets().stream().map(IProductMapper.INSTANCE::toDtoResponse).toList();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getProducts(@RequestParam(required = false) String name,
                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return productService.gets(name, pageable).map(IProductMapper.INSTANCE::toDtoResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getCategory(@PathVariable String id) {
        return IProductMapper.INSTANCE.toDtoResponse(productService.get(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ProductRequest request) {
        productService.create(IProductMapper.INSTANCE.toEntity(request));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid ProductRequest request) {
        productService.update(id, IProductMapper.INSTANCE.toEntity(request));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        productService.delete(id);
    }
}
