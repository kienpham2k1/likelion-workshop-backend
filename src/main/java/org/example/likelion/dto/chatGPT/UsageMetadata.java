package org.example.likelion.dto.chatGPT;

import lombok.Data;

@Data
public class UsageMetadata {
    public int promptTokenCount;
    public int candidatesTokenCount;
    public int totalTokenCount;
}
