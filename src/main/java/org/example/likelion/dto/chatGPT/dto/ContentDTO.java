package org.example.likelion.dto.chatGPT.dto;

import lombok.*;
import org.example.likelion.dto.chatGPT.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDTO {
    private Role role;
    private List<PartDTO> parts;
}