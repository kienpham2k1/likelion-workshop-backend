package org.example.likelion.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String color;
    private String imgLink;
    private int size;
    private int quantity;
    private double price;
    private CategoryResponse category;
}
