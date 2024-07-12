package org.example.likelion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.chatGPT.*;
import org.example.likelion.dto.mapper.IProductMapper;
import org.example.likelion.dto.request.AIRecommendationRequest;
import org.example.likelion.dto.response.AIRecommendationResponse;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.model.Product;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.utils.APICallerUtils;
import org.example.likelion.utils.GeminiApiUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    @Value("${ai.endpoint}")
    private String apiUrl;
    private final APICallerUtils apiCallerUtils;
    private final GeminiApiUtils geminiApiUtils;

    private final ProductRepository productRepository;
    private final IProductMapper iProductMapper;
    private String PROMPTS = "You are an AI assistant that recommends shoes based on the situation described by the user. " +
            "If the user asks a question not related to shoes, respond with 'I am just a Shoes Recommendation Assistant.'." +
            "Interpret any gender, preferences, or location details as requests for shoe recommendations. " +
            "Recommend a maximum of 3 colors and 3 types. " +
            "But don't necessarily to recommend 3 all times. " +
            "If the user uploads an image, interpret it as their outfit and recommend suitable shoes. " +
            "Please describe their outfit a little bit. ";
    private String PROMPT_RESPONSE_TYPE = "Respond in JSON format: ";
    private String PROMPT_CATEGORIES_SHOES = "Recommend shoes only from the following types: ";
    private String PROMPT_COLORS_SHOES = "Recommend shoes only from the following colors: ";
    private String RETURN_JSON_FORMAT = "{colors: List[string], shoesTypes: List[shoesType], description: stri[ng, reasonToChooseThis: string}.";

    @Override
    public AIRecommendationResponse getRecommendation(AIRecommendationRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");

        ResponseEntity<AIRecommendationResponse> e = apiCallerUtils.callApi(apiUrl, request, headers, HttpMethod.POST, AIRecommendationResponse.class);
        AIRecommendationResponse rp = e.getBody();
        Pageable pageable = PageRequest.of(0, 10);
        assert rp != null;
        List<ProductResponse> productsRecommendationRp = productRepository.findAllByFilter(rp.getShoes_type() == null ? null : rp.getShoes_type().stream().map(String::toUpperCase).toList()
                        , rp.getColor() == null ? null : rp.getColor().stream().map(String::toUpperCase).toList(), pageable)
                .stream()
                .map(iProductMapper::toDtoResponse)
                .collect(Collectors.toList());
        rp.setProductResponses(productsRecommendationRp);
        return rp;
    }

    @Override
    public GeminiAIResponse getRecommendation(GeminiAIRequest geminiAIRequest) {
        if (geminiAIRequest.getContents().size() <= 1) {
            List<String> categories = productRepository.findProductCategories();
            List<String> colors = productRepository.findProductColors();
            PROMPT_CATEGORIES_SHOES += categories.toString();
            PROMPT_COLORS_SHOES += colors.toString();
            PROMPT_RESPONSE_TYPE += RETURN_JSON_FORMAT;
            PROMPTS += PROMPT_RESPONSE_TYPE + PROMPT_CATEGORIES_SHOES + PROMPT_COLORS_SHOES;
            geminiAIRequest.getContents().add(0, new Content(Role.user, Collections.singletonList(new Part(PROMPTS))));
        }
        var rs = geminiApiUtils.getGeminiAIResponse(geminiAIRequest);
        Content contentRs = rs.getCandidates().get(0).getContent();
        String textRS = contentRs.getParts().get(0).getText();
        String afterReplace = textRS.replace("json", "")
                .replace("```", "")
                .replace("\n", "")
                .replace("\\", "");

        Pageable pageable = PageRequest.of(0, 10);
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();

        JsonRecommendReturnType recommendation = null;
        try {
            recommendation = objectMapper.readValue(afterReplace, JsonRecommendReturnType.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert recommendation != null;
        List<ProductResponse> productsRecommendationRp = new ArrayList<>();
        for (Product product : productRepository
                .findAllByFilter(recommendation.getShoesTypes() == null ? null : recommendation.getShoesTypes().stream().map(String::toUpperCase).toList()
                        , recommendation.getColors() == null ? null : recommendation.getColors().stream().map(String::toUpperCase).toList(), pageable)) {
            ProductResponse dtoResponse = iProductMapper.toDtoResponse(product);
            productsRecommendationRp.add(dtoResponse);
        }
        recommendation.setProductsRecommend(productsRecommendationRp);
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(recommendation);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        contentRs.getParts().get(0).setText(jsonString);
        rs.getCandidates().get(0).setContent(contentRs);
        return rs;
    }
}
