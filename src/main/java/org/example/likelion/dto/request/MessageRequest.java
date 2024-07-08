package org.example.likelion.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
public class MessageRequest {
    private String content;
    private MultipartFile attachment;
    @JsonIgnore
    private LocalDate createdDate;

}
