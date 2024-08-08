package org.example.likelion.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RoomChatResponse {
    private String id;
    private LocalDate createdDate;
    private UserResponse user;
}
