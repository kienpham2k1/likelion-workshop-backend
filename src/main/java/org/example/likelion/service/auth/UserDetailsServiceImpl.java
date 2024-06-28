package org.example.likelion.service.auth;

import jakarta.transaction.Transactional;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.model.User;
import org.example.likelion.repository.AdminRepository;
import org.example.likelion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> userDetails = Optional.empty();
        userDetails = userRepository.findUserDetailsByUsername(username);
        if (userDetails.isEmpty()) {
            userDetails = adminRepository.findUserDetailsByUsername(username);
        }
        return userDetails.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }


}
