package org.example.likelion.dto.chatGPT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.likelion.dto.chatGPT.JsonRecommendReturnType;
import org.example.likelion.dto.response.ProductResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RSAskDTO {
    JsonRecommendReturnType jsonRecommendReturnType;
    ProductResponse productResponse;
}
