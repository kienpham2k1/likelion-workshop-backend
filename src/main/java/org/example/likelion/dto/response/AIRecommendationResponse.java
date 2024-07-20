package org.example.likelion.dto.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIRecommendationResponse {
    private List<String> shoes_type;
    private List<String> color;
    private String description;
    private List<ProductResponse> productResponses;
}
