package org.example.likelion.dto.chatGPT;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SafetyRating {
    private String category;
    private String probability;
}
