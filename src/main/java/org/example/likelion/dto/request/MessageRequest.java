package org.example.likelion.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MessageRequest {
    private String content;
    private MultipartFile attachment;
    private String userId;
    private String roomId;
}
