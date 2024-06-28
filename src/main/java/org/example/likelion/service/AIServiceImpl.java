package org.example.likelion.service;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IProductMapper;
import org.example.likelion.dto.request.AIRecommendationRequest;
import org.example.likelion.dto.response.AIRecommendationResponse;
import org.example.likelion.dto.response.ProductResponse;
import org.example.likelion.repository.ProductRepository;
import org.example.likelion.utils.APICallerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    @Value("${ai.endpoint}")
    private String endpoint;
    private final APICallerUtils apiCallerUtils;
    private final ProductRepository productRepository;
    private final IProductMapper iProductMapper;

    @Override
    public AIRecommendationResponse getRecommendation(AIRecommendationRequest request) {
        AIRecommendationResponse rp = (AIRecommendationResponse) apiCallerUtils.callApi(endpoint, HttpMethod.POST, request, AIRecommendationResponse.class);
        Pageable pageable =  PageRequest.of(0, 10);
        List<ProductResponse> productsRecommendationRp = productRepository.findAllByFilter(rp.getShoes_type() == null ? null : rp.getShoes_type().stream().map(String::toUpperCase).toList()
                        , rp.getColor()  == null ? null : rp.getColor().stream().map(String::toUpperCase).toList(), pageable)
                .stream()
                .map(iProductMapper::toDtoResponse)
                .collect(Collectors.toList());
        rp.setProductResponses(productsRecommendationRp);
        return rp;
    }
}
