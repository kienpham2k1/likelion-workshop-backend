package org.example.likelion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.likelion.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    @Min(1)
    private double total;
    private double shippingFee;
    @NotNull
    @NotEmpty
    private String phoneNumber;
    @NotNull
    @NotEmpty
    private String addressLine;
    @JsonIgnore
    private OrderStatus orderStatus = OrderStatus.RECEIVED;
    @NotEmpty
    @Valid
    private List<OrderDetailRequest> orderDetails;
}
