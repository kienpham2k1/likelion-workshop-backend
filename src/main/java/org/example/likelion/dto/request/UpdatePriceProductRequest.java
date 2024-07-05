package org.example.likelion.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePriceProductRequest {
    @Min(0)
    @NotNull
    private int price;
}
