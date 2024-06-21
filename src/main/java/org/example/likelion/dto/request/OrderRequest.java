package org.example.likelion.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.likelion.dto.response.Voucher;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private double total;
    private double shippingFee;
    private String phoneNumber;
    private String addressLine;
    private LocalDate createDate;
    @NotEmpty
    @Size(min=1)
    List<OrderDetailRequest> orderDetails;
}
