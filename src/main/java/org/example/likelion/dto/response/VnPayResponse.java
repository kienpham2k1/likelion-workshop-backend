package org.example.likelion.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VnPayResponse {
    private boolean status;
    private String orderInfo;
    private String totalPrice;
    private String paymentTime;
    private String transactionId;
}
