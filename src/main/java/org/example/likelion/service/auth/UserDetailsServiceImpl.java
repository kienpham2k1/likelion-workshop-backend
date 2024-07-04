package org.example.likelion.service.auth;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.example.likelion.repository.AccountRepository;
import org.example.likelion.repository.AdminRepository;
import org.example.likelion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Tuple tuple = accountRepository.findUserDetailsByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Optional<UserDetails> userDetails = Optional.empty();
        if (Objects.equals(tuple.get("role").toString(), "USER")) {
            userDetails = userRepository.findUserDetailsByUsername(username);
        } else if (Objects.equals(tuple.get("role").toString(), "ADMIN")){
            userDetails = adminRepository.findUserDetailsByUsername(username);
        }
        return userDetails.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }


}
