package org.example.likelion.dto.request.infobip;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Message {
    private List<Recipient> destinations;
    private String from;
    private String text;
}
