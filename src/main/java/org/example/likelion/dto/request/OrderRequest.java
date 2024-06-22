package org.example.likelion.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Data
public class OrderRequest {
    private double total;
    private double shippingFee;
    @NotNull
    @NotEmpty
    private String phoneNumber;
    private String addressLine;
    private LocalDate createDate;
    @NotEmpty
    @Valid
    private List<OrderDetailRequest> orderDetails;
}
