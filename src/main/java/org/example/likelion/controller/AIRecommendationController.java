package org.example.likelion.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.GeminiAIRequest;
import org.example.likelion.dto.chatGPT.GeminiAIResponse;
import org.example.likelion.service.AIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public GeminiAIResponse getRecommendation(@ModelAttribute FileUploadDTO dto) {
        FileArchive fileArchive = dto.getFileArchive();
        String text = dto.getText();
        MultipartFile file = fileArchive.getFile();
        return null;
//        return aiService.getRecommendation(geminiAIRequest, null);
    }

    @Data
    private class FileUploadDTO {
        private FileArchive fileArchive;
        private String text;
    }
    @Data
    private class FileArchive{
        private MultipartFile file;
    }
}
