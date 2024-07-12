package org.example.likelion.service;

import org.example.likelion.dto.request.AIRecommendationRequest;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.response.AIRecommendationResponse;
import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AIService {
    AIRecommendationResponse getRecommendation(AIRecommendationRequest request);
    GeminiAIResponse getRecommendation(GeminiAIRequest geminiAIRequest);

}
