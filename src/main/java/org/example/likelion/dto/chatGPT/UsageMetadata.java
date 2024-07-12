package org.example.likelion.dto.chatGPT;

import lombok.Data;

@Data
public class UsageMetadata {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
}
