package org.example.likelion.service;

import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.request.UpdatePriceProductRequest;
import org.example.likelion.dto.request.UpdateQuantityProductRequest;
import org.example.likelion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> gets(String productName);

    Page<Product> gets(String name, String categoryId, List<Integer> sizes, List<String> colors, Double priceMin, Double priceMax, Pageable pageable);

    Product get(String id);

    Product create(Product product, MultipartFile img);

    Product update(String id, ProductRequest product, MultipartFile img);

    Product reduce(String id, int quantity);

    void delete(String id);

    boolean isStocking(String id, int quantity);

    Product updateQuantity(String id, UpdateQuantityProductRequest request);

    List<Product> updateProductPrice(String name, UpdatePriceProductRequest request);
}
