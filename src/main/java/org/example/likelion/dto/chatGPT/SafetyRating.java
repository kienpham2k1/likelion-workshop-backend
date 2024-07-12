package org.example.likelion.dto.chatGPT;

import lombok.Data;

@Data
public class SafetyRating {
    private String category;
    private String probability;
}
