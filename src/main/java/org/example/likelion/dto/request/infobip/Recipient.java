package org.example.likelion.dto.request.infobip;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipient {
    private String to;
}
