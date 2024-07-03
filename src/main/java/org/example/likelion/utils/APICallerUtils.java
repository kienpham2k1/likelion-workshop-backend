package org.example.likelion.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class APICallerUtils {

    private final RestTemplate restTemplate;

    public <T, R> ResponseEntity<R> callApi(String apiUrl, T requestBody, HttpHeaders headers, HttpMethod httpMethod, Class<R> responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(apiUrl, httpMethod, requestEntity, responseType);
    }
}
