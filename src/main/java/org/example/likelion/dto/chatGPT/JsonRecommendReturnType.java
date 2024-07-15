package org.example.likelion.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.likelion.dto.response.ProductResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonRecommendReturnType {
    @JsonProperty("colors")
    private List<String> colors;

    @JsonProperty("shoesTypes")
    private List<String> shoesTypes;

    @JsonProperty("description")
    private String description;

    @JsonProperty("reasonToChooseThis")
    private String reasonToChooseThis;

    @JsonProperty("productsRecommend")
    private List<ProductResponse> productsRecommend;

}
