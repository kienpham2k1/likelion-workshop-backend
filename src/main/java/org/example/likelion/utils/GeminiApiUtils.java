package org.example.likelion.utils;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiApiUtils {
    @Value("${gemini.api.key}")
    private String KEY;
    private final APICallerUtils apiCallerUtils;
    @Value("${gemini.api.endpoint}")
    private String GEMINI_API_CHAT_ENDPOINT;

    public GeminiAIResponse getGeminiAIResponse(GeminiAIRequest geminiAIRequest) {
        String url = GEMINI_API_CHAT_ENDPOINT + "?key=" + KEY;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<GeminiAIResponse> e = apiCallerUtils.callApi(url, geminiAIRequest, headers, HttpMethod.POST, GeminiAIResponse.class);
        return e.getBody();
    }
}