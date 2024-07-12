package org.example.likelion.service.implService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.mapper.IProductMapperImpl;
import org.example.likelion.dto.request.ProductRequest;
import org.example.likelion.dto.request.UpdatePriceProductRequest;
import org.example.likelion.dto.request.UpdateQuantityProductRequest;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Product;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.service.ProductService;
import org.example.likelion.service.cloud.s3.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileService fileService;

    @Override
    public Page<Product> gets(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> gets(String productName) {
        return productRepository.findAllByName(productName);
    }

    @Override
    public Page<Product> gets(String name, String categoryId, List<Integer> sizes, List<String> colors, Double priceMin, Double priceMax, Pageable pageable) {
        return productRepository.findAllByFilter(name, categoryId, sizes, (colors == null ? null : colors.stream().map(String::toUpperCase).toList()), priceMin, priceMax, pageable);
    }

    @Override
    public Product get(String id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Product create(Product product, MultipartFile img) {
        String imgLink;
        try {
            imgLink = fileService.uploadFile(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (imgLink != null) {
            product.setImgLink(imgLink);
            return productRepository.save(product);
        } else throw new EntityNotFoundException("");
    }

    @Override
    @Transactional
    public Product update(String id, ProductRequest request, MultipartFile img) {
        Product cur = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        try {
            var imgLink = fileService.uploadFile(img);
            cur.setImgLink(imgLink);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IProductMapperImpl.INSTANCE.updateEntityFromRequest(request, cur);
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
