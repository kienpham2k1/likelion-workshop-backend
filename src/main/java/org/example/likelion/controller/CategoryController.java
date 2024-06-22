package org.example.likelion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.ICategoryMapper;
import org.example.likelion.dto.request.CategoryRequest;
import org.example.likelion.dto.response.CategoryResponse;
import org.example.likelion.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getCategories() {
        return categoryService.gets().stream().map(ICategoryMapper.INSTANCE::toDtoResponse).toList();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryResponse> getCategories(@RequestParam(required = false) String name,
                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "asc") String sortDirection,
                                                @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return categoryService.gets(name, pageable).map(ICategoryMapper.INSTANCE::toDtoResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategory(@PathVariable String id) {
        return ICategoryMapper.INSTANCE.toDtoResponse(categoryService.get(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CategoryRequest request) {
        categoryService.create(ICategoryMapper.INSTANCE.toEntity(request));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid CategoryRequest request) {
        categoryService.update(id, ICategoryMapper.INSTANCE.toEntity(request));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        categoryService.delete(id);
    }
}
