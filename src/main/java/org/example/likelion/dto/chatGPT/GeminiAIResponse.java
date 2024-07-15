package org.example.likelion.dto.chatGPT;

import lombok.*;
import org.example.likelion.dto.chatGPT.Candidate;
import org.example.likelion.dto.chatGPT.Content;
import org.example.likelion.dto.chatGPT.UsageMetadata;

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
