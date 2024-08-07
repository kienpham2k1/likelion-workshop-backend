package org.example.likelion.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {
    @NotNull
    @NotEmpty
    private String productId;
    @Min(1)
    private int quantity;
}
