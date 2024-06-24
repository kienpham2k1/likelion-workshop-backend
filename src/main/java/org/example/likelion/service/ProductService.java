package org.example.likelion.service;

import org.example.likelion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> gets(String productName);

    Page<Product> gets(String name, Pageable pageable);

    Product get(String id);

    Product create(Product product);

    Product update(String id, Product product);

    Product reduce(String id, int quantity);

    void delete(String id);

    boolean isStocking(String id, int quantity);

}
