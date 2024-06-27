package org.example.likelion.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @NotNull
    @NotEmpty
    private String name;
    private String description;
    @NotNull
    @NotEmpty
    private String color;
    private String imgLink;
    @Positive
    private int size;
    @Min(0)
    @NotNull
    private int quantity;
    @Min(1)
    @NotNull
    private double price;
    @NotNull
    private String categoryId;
}
