package org.example.likelion.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Part {
    private String text;
    @JsonProperty("inline_data")
    private InlineData inlineData;

    public Part(String text) {
        this.text = text;
    }
}
