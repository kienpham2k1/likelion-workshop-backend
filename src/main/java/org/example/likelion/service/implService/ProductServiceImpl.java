package org.example.likelion.service.implService;

import lombok.AllArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.mapper.IProductMapperImpl;
import org.example.likelion.dto.request.UpdatePriceProductRequest;
import org.example.likelion.dto.request.UpdateQuantityProductRequest;
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
    public Page<Product> gets(String name, String categoryId, List<Integer> sizes, List<String> colors, Double priceMin, Double priceMax, Pageable pageable) {
        return productRepository.findByNameContainsIgnoreCase(name, categoryId, sizes, colors, priceMin, priceMax, pageable);
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

    @Override
    public Product updateQuantity(String id, UpdateQuantityProductRequest request) {
        Product cur = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        cur.setQuantity(request.getQuantity());
        return productRepository.save(cur);
    }

    @Override
    public List<Product> updateProductPrice(String name, UpdatePriceProductRequest request) {
        List<Product> products = gets(name);

        for (Product product : products) {
            product.setPrice(request.getPrice());
        }

        return productRepository.saveAll(products);
    }
}
