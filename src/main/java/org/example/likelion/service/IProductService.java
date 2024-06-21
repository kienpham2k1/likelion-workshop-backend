package org.example.likelion.service;

import org.example.likelion.model.Category;
import org.example.likelion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<Product> gets();

    Page<Product> gets(String name, Pageable pageable);

    Product get(String id);

    void create(Product product);

    void update(String id, Product product);

    void delete(String id);
}
