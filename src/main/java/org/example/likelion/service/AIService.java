package org.example.likelion.service;

import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;
import org.example.likelion.dto.response.AIRecommendationResponse;

public interface AIService {
    AIRecommendationResponse getRecommendation(String request);

    GeminiAIResponseDTO getRecommendation(GeminiAIRequest geminiAIRequest);

}
