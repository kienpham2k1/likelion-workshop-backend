package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IProductMapper;
import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.request.UpdatePriceProductRequest;
import org.example.likelion.dto.request.UpdateQuantityProductRequest;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name = "Product Resource")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get Product By Name")
    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.gets().stream().map(IProductMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Product By Name")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(@RequestParam String productName) {
        return productService.gets(productName).stream().map(IProductMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get List Of Product Filter")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getProducts(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String categoryId,
                                             @RequestParam(required = false) List<Integer> sizes,
                                             @RequestParam(required = false) List<String> colors,
                                             @RequestParam(required = false) Double priceMin,
                                             @RequestParam(required = false) Double priceMax,
                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                             @RequestParam(defaultValue = "price") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return productService.gets(name, categoryId, sizes, colors, priceMin, priceMax, pageable).map(IProductMapper.INSTANCE::toDtoResponse);
    }

    @Operation(summary = "Get Detail Product By ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getCategory(@PathVariable String id) {
        return IProductMapper.INSTANCE.toDtoResponse(productService.get(id));
    }

    @Operation(summary = "Create Product")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestPart(name = "product") @Valid ProductRequest request, @RequestPart(name = "img") MultipartFile img) {
        productService.create(IProductMapper.INSTANCE.toEntity(request), img);
    }

    @Operation(summary = "Update Product")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestPart(name = "product") @Valid ProductRequest request, @RequestPart(name = "img") MultipartFile img) {
        productService.update(id, request, img);
    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }

    @Operation(summary = "Update Product Quantity")
    @PutMapping("/updateQuantity/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuantity(@PathVariable String id, @RequestBody @Valid UpdateQuantityProductRequest request) {
        productService.updateQuantity(id, request);
    }

    @Operation(summary = "update Product Price")
    @PutMapping("/updateProductPrice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductPrice(@PathVariable String name, @RequestBody @Valid UpdatePriceProductRequest request) {
        productService.updateProductPrice(name, request);
    }
}
