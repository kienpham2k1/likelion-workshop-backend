package org.example.likelion.dto.chatGPT;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiAIRequest {
    private List<Content> contents;
}
