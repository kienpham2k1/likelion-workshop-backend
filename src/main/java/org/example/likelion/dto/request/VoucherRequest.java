package org.example.likelion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Min(0)
    private double discountPercent;
    @NotNull
    private LocalDate expired_date;
    @JsonIgnore
    @NotNull
    private LocalDate create_date = LocalDate.now();
    @NotNull
    @Min(0)
    private int quantity;
    @NotNull
    @JsonIgnore
    private boolean deleted = false;
    @NotNull
    @JsonIgnore
    private boolean active = true;
}
