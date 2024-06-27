package org.example.likelion.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class APICallerUtils {

    private RestTemplate restTemplate;

    public APICallerUtils() {
        this.restTemplate = new RestTemplate();
    }

    public <T> T callApi(String url, HttpMethod httpMethod,Object requestBody, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<>(requestBody), responseType);
        return responseEntity.getBody();
    }
}
