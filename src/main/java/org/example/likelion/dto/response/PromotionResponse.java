package org.example.likelion.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionResponse {
    private String id;
    private String name;
    private double discountPercent;
    private LocalDate expiredDate;
    private LocalDate startedDate;
    private boolean active;
    private String categoryId;
}
