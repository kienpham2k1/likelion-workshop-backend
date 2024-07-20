package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;
import org.example.likelion.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai-recommendation")
public class AIRecommendationController {
    private final AIService aiService;

    @PostMapping("/ask")
    @ResponseStatus(HttpStatus.OK)
    public GeminiAIResponseDTO getRecommendation(@RequestBody GeminiAIRequest geminiAIRequest) {
        return aiService.getRecommendation(geminiAIRequest);
    }
}
