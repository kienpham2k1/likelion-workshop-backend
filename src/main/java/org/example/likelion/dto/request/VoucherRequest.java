package org.example.likelion.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherRequest {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    @Min(0)
    private double discountPercent;
    @NotNull
    @NotEmpty
    private LocalDate expired_date;
    @NotNull
    @NotEmpty
    private LocalDate create_date;
    @NotNull
    @NotEmpty
    @Min(0)
    private int quantity;
    @NotNull
    @NotEmpty
    private boolean isDelete;
    @NotNull
    @NotEmpty
    private boolean isActive;
}
