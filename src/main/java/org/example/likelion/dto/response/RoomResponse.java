package org.example.likelion.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RoomResponse {
    private String id;
    private LocalDate createdDate;
}
