package org.example.likelion.service;

import org.example.likelion.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ICategoryService {
    List<Category> gets();

    Page<Category> gets(String name, Pageable pageable);

    Category get(String id);

    Category create(Category category);

    Category update(String id, Category category);

    void delete(String id);
}
