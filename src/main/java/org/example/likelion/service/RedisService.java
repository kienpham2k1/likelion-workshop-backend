package org.example.likelion.service;


import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;

public interface RedisService {

    public void insertValue(String key, GeminiAIResponseDTO value);

    public GeminiAIResponseDTO searchByKey(String key);

}
