package org.example.likelion.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineData {
    @JsonProperty("mime_type")
    private String mimeType;
    private String data;
}
