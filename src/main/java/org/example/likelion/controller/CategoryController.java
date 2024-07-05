package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.ICategoryMapper;
import org.example.likelion.dto.request.CategoryRequest;
import org.example.likelion.dto.response.CategoryResponse;
import org.example.likelion.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "Category Resource")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get Category List")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getCategories() {
        return categoryService.gets().stream().map(ICategoryMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Category by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategory(@PathVariable String id) {
        return ICategoryMapper.INSTANCE.toDtoResponse(categoryService.get(id));
    }

    @Operation(summary = "Create Category")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CategoryRequest request) {
        categoryService.create(ICategoryMapper.INSTANCE.toEntity(request));
    }

    @Operation(summary = "Update Category")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid CategoryRequest request) {
        categoryService.update(id, ICategoryMapper.INSTANCE.toEntity(request));
    }

    @Operation(summary = "Delete Category")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        categoryService.delete(id);
    }
}
