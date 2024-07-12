package org.example.likelion.dto.chatGPT;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.likelion.dto.chatGPT.Candidate;
import org.example.likelion.dto.chatGPT.Content;
import org.example.likelion.dto.chatGPT.UsageMetadata;

import java.util.List;
@Data
public class GeminiAIResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}
