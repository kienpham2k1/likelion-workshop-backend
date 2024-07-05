package org.example.likelion.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherResponse {
    private String id;
    private String name;
    private double discountPercent;
    private LocalDate expired_date;
    private LocalDate create_date;
    private int quantity;
    private boolean deleted;
    private boolean active;
}
