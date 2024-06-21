package org.example.likelion.service.implService;

import lombok.AllArgsConstructor;
import org.example.likelion.model.Product;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> gets() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> gets(String name, Pageable pageable) {
        return productRepository.findByNameIsContainingIgnoreCase(name, pageable);
    }

    @Override
    public Product get(String id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(String id, Product product) {
        productRepository.findById(id).orElseThrow();
        productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.findById(id).orElseThrow();
        productRepository.deleteById(id);
    }
}
