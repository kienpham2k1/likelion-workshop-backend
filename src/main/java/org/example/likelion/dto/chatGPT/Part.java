package org.example.likelion.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    private String text;
    @JsonProperty("inline_data")
    private InlineData inlineData;

    public Part(String text) {
        this.text = text;
    }
}
