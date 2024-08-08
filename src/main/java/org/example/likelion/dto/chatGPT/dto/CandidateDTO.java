package org.example.likelion.dto.chatGPT.dto;

import lombok.*;
import org.example.likelion.dto.chatGPT.SafetyRating;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDTO {
    private ContentDTO content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;
}
