package org.example.likelion.utils;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiApiUtils {
    //    @Value("${openai.api.key}")
    private String key = "AIzaSyDJhHMCfl7omwjGAVEQolN4cSRwiXZU_uU";
    private final APICallerUtils apiCallerUtils;
    private String GEMINI_API_CHAT_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + key;
    public GeminiAIResponse getGeminiAIResponse(GeminiAIRequest geminiAIRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<GeminiAIResponse> e = apiCallerUtils.callApi(GEMINI_API_CHAT_ENDPOINT, geminiAIRequest, headers, HttpMethod.POST, GeminiAIResponse.class);
        return e.getBody();
    }
}