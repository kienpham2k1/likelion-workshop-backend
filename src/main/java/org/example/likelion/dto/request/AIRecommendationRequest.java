package org.example.likelion.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIRecommendationRequest {
    private String chat;
    private String img;

}
