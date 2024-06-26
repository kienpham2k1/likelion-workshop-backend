package org.example.likelion.utils;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CheckValidResourceToUser {

    public static boolean isValidToUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                if (userDetails.getId().equals(userId)) return true;
                if (userDetails.getRole().equals(Role.ADMIN)) return true;
            }
        }
        return false;
    }
}
