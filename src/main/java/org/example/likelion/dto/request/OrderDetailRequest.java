package org.example.likelion.dto.request;

import lombok.*;
import org.example.likelion.dto.response.ProductResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {
    private String productId;
    private int quantity;
}
