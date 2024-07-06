package org.example.likelion.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class RoomChatResponse {
    private String id;

    private LocalDate createdDate;

    private Set<MessageResponse> messages;
}
