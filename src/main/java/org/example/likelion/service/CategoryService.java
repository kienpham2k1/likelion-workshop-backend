package org.example.likelion.service;

import org.example.likelion.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> gets();

    Category get(String id);

    Category create(Category category);

    Category update(String id, Category category);

    void delete(String id);
}
