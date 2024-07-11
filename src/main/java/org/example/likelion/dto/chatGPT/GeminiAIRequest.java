package org.example.likelion.dto.chatGPT;

import lombok.*;
import org.example.likelion.dto.chatGPT.Content;

import java.util.List;
import java.util.Stack;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiAIRequest {
    List<Content> contents;
}
