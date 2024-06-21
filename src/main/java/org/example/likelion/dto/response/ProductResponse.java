package org.example.likelion.dto.response;

import lombok.*;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String color;
    private int size;
    private int quantity;
    private double price;
    private CategoryResponse category;
}
