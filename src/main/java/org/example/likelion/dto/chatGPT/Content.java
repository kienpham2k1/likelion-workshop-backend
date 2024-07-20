package org.example.likelion.dto.chatGPT;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    private Role role;
    private List<Part> parts;
}