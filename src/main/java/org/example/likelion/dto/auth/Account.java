package org.example.likelion.dto.auth;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Account {
    private String id;
    private String username;
    private String password;
    private Role role;
}
