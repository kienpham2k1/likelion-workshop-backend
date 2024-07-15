package org.example.likelion.dto.chatGPT;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiAIRequest {
    private List<Content> contents;
}
