package org.example.likelion.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private double total;
    private double shippingFee;
    private String phoneNumber;
    private String addressLine;
    private LocalDate createDate;
    List<OrderDetailResponse> orderDetails;
}
