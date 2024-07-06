package org.example.likelion.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MessageResponse {
    private String id;
    private String content;
    private String attachment;
    private LocalDate createdDate;
    private String userId;
    private String roomId;
}
