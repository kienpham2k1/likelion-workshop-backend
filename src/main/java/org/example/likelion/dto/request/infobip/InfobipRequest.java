package org.example.likelion.dto.request.infobip;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InfobipRequest {
    private List<Message> messages;
}
