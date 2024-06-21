package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.model.Category;
import org.example.likelion.repository.CategoryRepository;
import org.example.likelion.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> gets() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> gets(String name, Pageable pageable) {
        return categoryRepository.findByNameIsContainingIgnoreCase(name, pageable);
    }

    @Override
    public Category get(String id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(String id, Category category) {
        categoryRepository.findById(id).orElseThrow();
        categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        categoryRepository.findById(id).orElseThrow();
        categoryRepository.deleteById(id);
    }
}
