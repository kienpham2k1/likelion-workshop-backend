package org.example.likelion.service;

import org.example.likelion.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getList();

    Product get(String id);
}
