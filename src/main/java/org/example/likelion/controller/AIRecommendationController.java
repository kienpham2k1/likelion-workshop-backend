package org.example.likelion.controller;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.Content;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;
import org.example.likelion.service.AIService;
import org.example.likelion.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai-recommendation")
public class AIRecommendationController {
    private final AIService aiService;
    private final RedisService redisService;


    @PostMapping("/ask")
    @ResponseStatus(HttpStatus.OK)
    public GeminiAIResponseDTO getRecommendation(@RequestBody GeminiAIRequest geminiAIRequest) {
        List<Content> geminiAIRequestContents = geminiAIRequest.getContents();
        int geminiAIRequestPartsSize = geminiAIRequestContents.get(geminiAIRequestContents.size() - 1).getParts().size();
        GeminiAIResponseDTO geminiAIResponseDTO;
        if (geminiAIRequestPartsSize == 1) {

            //read from Redis
            GeminiAIResponseDTO redisCacheValue = redisService.searchByKey(geminiAIRequestContents.get(geminiAIRequestContents.size() - 1).getParts().get(0).getText());
            if (redisCacheValue != null)
                return redisCacheValue;

            // write to cache
            geminiAIResponseDTO = aiService.getRecommendation(geminiAIRequest);
            redisService.insertValue(geminiAIRequestContents.get(geminiAIRequestContents.size() - 1).getParts().get(0).getText(), geminiAIResponseDTO);
            return geminiAIResponseDTO;
        }
        return aiService.getRecommendation(geminiAIRequest);
    }
}