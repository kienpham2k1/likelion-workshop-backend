package org.example.likelion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.likelion.model.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionRequest {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @Min(0)
    private double discountPercent;
    private LocalDate expiredDate;

    @NotNull
    private LocalDate startedDate;
    private List<String> category_id;
    @JsonIgnore
    private Set<Category> categories;
    @JsonIgnore
    private boolean active = true;

}
