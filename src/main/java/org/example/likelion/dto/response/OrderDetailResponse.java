package org.example.likelion.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private ProductResponse product;
    private int quantity;
    private String orderId;
}
