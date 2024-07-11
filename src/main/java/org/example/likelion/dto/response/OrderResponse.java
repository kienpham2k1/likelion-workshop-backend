package org.example.likelion.dto.response;

import lombok.*;
import org.example.likelion.enums.OrderStatus;

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
    private UserResponse user;
    private OrderStatus orderStatus;
    private String voucherId;
    private boolean cancel;
    private boolean paid;
    private boolean onlinePayment;
}
