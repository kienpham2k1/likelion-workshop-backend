package org.example.likelion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.likelion.dto.chatGPT.*;
import org.example.likelion.dto.chatGPT.dto.GeminiAIResponseDTO;
import org.example.likelion.dto.mapper.IGeminiAIMapper;
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
@Slf4j
public class AIServiceImpl implements AIService {

    @Value("${ai.endpoint}")
    private String apiUrl;
    private final APICallerUtils apiCallerUtils;
    private final GeminiApiUtils geminiApiUtils;

    private final ProductRepository productRepository;
    private final IProductMapper iProductMapper;
    private String PROMPTS = "You are an AI assistant that recommends shoes based on the situation described by the user. " +
            "Interpret any gender, preferences, or location details as requests for shoe recommendations. " +
            "Recommend a maximum of 3 colors and 3 types. " +
            "But don't necessarily to recommend 3 all times. " +
            "If the user uploads an image, interpret it as their outfit and recommend suitable shoes. " +
            "Please describe their outfit a little bit. ";
    private String PROMPT_RESPONSE_TYPE = "Respond in JSON format: ";
    private String PROMPT_CATEGORIES_SHOES = "Recommend shoes only from the following types: ";
    private String PROMPT_COLORS_SHOES = "Recommend shoes only from the following colors: ";
    private final String RETURN_JSON_FORMAT = "{colors: List[string], shoesTypes: List[shoesType], description: string, reasonToChooseThis: string}, messageError: null";
    private final String ERROR_RESPONSE = "If the user asks a question not related to shoes, response with {colors: null, shoesTypes: null, description: null, reasonToChooseThis: null, messageError: \"I am just a Shoes Recommendation Assistant.\"}";

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
    public GeminiAIResponseDTO getRecommendation(GeminiAIRequest geminiAIRequest) {
        List<String> categories = productRepository.findProductCategories();
        List<String> colors = productRepository.findProductColors();
        PROMPT_CATEGORIES_SHOES += categories.toString();
        PROMPT_COLORS_SHOES += colors.toString();
        PROMPT_RESPONSE_TYPE += RETURN_JSON_FORMAT;
        PROMPTS += PROMPT_RESPONSE_TYPE + PROMPT_CATEGORIES_SHOES + PROMPT_COLORS_SHOES + ERROR_RESPONSE;
        geminiAIRequest.getContents().add(0, new Content(Role.user, Collections.singletonList(new Part(PROMPTS))));
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

        GeminiAIResponseDTO geminiAIResponseDTO = IGeminiAIMapper.INSTANCE.toDtoResponse(rs);
        JsonRecommendReturnType jsonRecommendReturnType = null;
        try {
            jsonRecommendReturnType = objectMapper.readValue(afterReplace, JsonRecommendReturnType.class);

            assert jsonRecommendReturnType != null;
            if (jsonRecommendReturnType.getMessageError() == null) {
                List<ProductResponse> productsRecommendationRp = new ArrayList<>();
                for (Product product : productRepository
                        .findAllByFilter(jsonRecommendReturnType.getShoesTypes() == null ? null : jsonRecommendReturnType.getShoesTypes().stream().map(String::toUpperCase).toList()
                                , jsonRecommendReturnType.getColors() == null ? null : jsonRecommendReturnType.getColors().stream().map(String::toUpperCase).toList(), pageable)) {
                    ProductResponse dtoResponse = iProductMapper.toDtoResponse(product);
                    productsRecommendationRp.add(dtoResponse);
                }
                jsonRecommendReturnType.setProductsRecommend(productsRecommendationRp);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        geminiAIResponseDTO.getCandidates().get(0).getContent().getParts().get(0).setJsonRecommendReturnType(jsonRecommendReturnType);
        return geminiAIResponseDTO;
    }
}
