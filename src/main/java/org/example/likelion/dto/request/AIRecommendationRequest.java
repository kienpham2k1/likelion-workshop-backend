package org.example.likelion.dto.request;

import lombok.*;

import java.util.List;

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
