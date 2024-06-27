package org.example.likelion.service;

import org.example.likelion.dto.request.AIRecommendationRequest;
import org.example.likelion.dto.response.AIRecommendationResponse;
import org.springframework.beans.factory.annotation.Value;

public interface AIService {
    AIRecommendationResponse getRecommendation(AIRecommendationRequest request);

}
