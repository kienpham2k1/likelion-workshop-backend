package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.example.likelion.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai-recommendation")
public class AIRecommendationController {
    private final AIService aiService;

    //    @PostMapping(value = "/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/ask")
    @ResponseStatus(HttpStatus.OK)
    public GeminiAIResponse getRecommendation(@RequestBody GeminiAIRequest geminiAIRequest) {
        return aiService.getRecommendation(geminiAIRequest);
    }
}
