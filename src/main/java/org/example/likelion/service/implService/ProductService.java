package org.example.likelion.service.implService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.likelion.model.Product;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getList() {
        return productRepository.findAll();
    }

    @Override
    public Product get(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}
