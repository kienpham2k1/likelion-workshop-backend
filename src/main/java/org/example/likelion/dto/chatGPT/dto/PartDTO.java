package org.example.likelion.dto.chatGPT.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.likelion.dto.chatGPT.InlineData;
import org.example.likelion.dto.chatGPT.JsonRecommendReturnType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartDTO {
    private JsonRecommendReturnType jsonRecommendReturnType;
    @JsonProperty("inline_data")
    private InlineData inlineData;
}
