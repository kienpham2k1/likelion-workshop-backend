package org.example.likelion.dto.chatGPT;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiAIResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}
