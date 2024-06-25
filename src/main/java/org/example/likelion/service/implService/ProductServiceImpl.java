package org.example.likelion.service.implService;

import lombok.AllArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.mapper.IProductMapperImpl;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Product;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> gets(String productName) {
        return productRepository.findAllByName(productName);
    }

    @Override
    public Page<Product> gets(String name, Pageable pageable) {
        return productRepository.findByNameContainsIgnoreCase(name, pageable);
    }

    @Override
    public Product get(String id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(String id, Product product) {
        Product cur = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        IProductMapperImpl.INSTANCE.updateEntityFromEntity(product, cur);
        return productRepository.save(cur);
    }

    @Override
    public Product reduce(String id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        productRepository.deleteById(id);
    }

    @Override
    public boolean isStocking(String id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        return product.getQuantity() >= quantity;
    }
}
