package org.example.likelion.utils;

import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ResourceUtils {

    public static boolean isValidResourceForUser(String userId) {
        boolean result = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                if (userDetails.getId().equals(userId)) {
                    result = true;
                } else if (userDetails.getRole().equals(Role.ADMIN)) {
                    result = true;
                }
            }
        }
        return result;
    }
}
