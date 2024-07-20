package org.example.likelion.dto.chatGPT.dto;

import lombok.*;
import org.example.likelion.dto.chatGPT.UsageMetadata;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiAIResponseDTO {
    private List<CandidateDTO> candidates;
    private UsageMetadata usageMetadata;
}
