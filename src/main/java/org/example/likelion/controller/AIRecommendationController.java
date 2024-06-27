package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.request.AIRecommendationRequest;
import org.example.likelion.dto.response.AIRecommendationResponse;
import org.example.likelion.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai-recommendation")
public class AIRecommendationController {
    private final AIService aiService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AIRecommendationResponse getRecommendation(@RequestBody AIRecommendationRequest request) {
      return   aiService.getRecommendation(request);

    }
}
