package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.mapper.ICategoryMapper;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Category;
import org.example.likelion.repository.CategoryRepository;
import org.example.likelion.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> gets() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> gets(String name, Pageable pageable) {
//        return categoryRepository.findByNameIsContainingIgnoreCase(name, pageable);
        return null;
    }

    @Override
    public Category get(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(String id, Category category) {
        Category cur = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
        ICategoryMapper.INSTANCE.updateEntityFromEntity(category, cur);
        return categoryRepository.save(cur);
    }

    @Override
    public void delete(String id) {
        Category cur = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
        cur.setDeleted(true);
        categoryRepository.save(cur);
    }
}
