package org.example.likelion.dto.chatGPT;

import lombok.Data;

import java.util.List;

@Data
public class Candidate {
    public Content content;
    public String finishReason;
    public int index;
    public List<SafetyRating> safetyRatings;
}
