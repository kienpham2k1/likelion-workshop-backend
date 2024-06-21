package org.example.likelion.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String color;
    private int size;
    private int quantity;
    private double price;
    private String categoryId;
}
