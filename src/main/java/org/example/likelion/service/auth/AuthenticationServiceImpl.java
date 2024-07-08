package org.example.likelion.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.Role;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.mapper.IAdminMapper;
import org.example.likelion.dto.mapper.IUserMapper;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.dto.response.UserResponse;
import org.example.likelion.enums.TokenType;
import org.example.likelion.exception.DuplicateRecordException;
import org.example.likelion.model.Token;
import org.example.likelion.model.User;
import org.example.likelion.repository.AccountRepository;
import org.example.likelion.repository.TokenRepository;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public UserRegisterResponse register(UserRegisterRequest registerRequest) {
        if (accountRepository.findAccountByUsername(registerRequest.getUsername()).isPresent()) {
            throw new DuplicateRecordException(ErrorMessage.USERNAME_HAS_TAKEN);
        }
        User user = IUserMapper.INSTANCE.toEntity(registerRequest);
        User savedUser = userRepository.save(user);
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        UserRegisterResponse userResponse = new UserRegisterResponse();
        userResponse.setUser(IUserMapper.INSTANCE.toDtoRegisterResponse(user));
        userResponse.setToken(new JwtResponse(jwtToken, refreshToken));
        return userResponse;
    }

    @Override
    public UserRegisterResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        Optional<User> userInfo = userRepository.findByUsername(loginRequest.getUsername());

        UserRegisterResponse userResponse = new UserRegisterResponse();
        userResponse.setUser(IUserMapper.INSTANCE.toDtoRegisterResponse(userInfo.orElse(null)));
        userResponse.setToken(new JwtResponse(accessToken, refreshToken));

        return userResponse;
    }

    @Override
    public void saveUserToken(UserDetailsImpl userDetails, String jwtToken) {
        var token = userDetails.getRole() == Role.USER ? Token.builder()
                .user(IUserMapper.INSTANCE.toEntity(userDetails))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build() : Token.builder()
                .admin(IAdminMapper.INSTANCE.toEntity(userDetails))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(UserDetailsImpl user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.getUserNameFromJwtToken(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = JwtResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public Optional<UserDetailsImpl> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                return Optional.of(userDetails);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserResponse> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                Optional<User> userInfo = userRepository.findByUsername(userDetails.getUsername());

                UserResponse userResponse = IUserMapper.INSTANCE.toDtoRegisterResponse(userInfo.orElse(null));
                return Optional.of(userResponse);
            }
        }
        return Optional.empty();
    }

    @Override
    public void logout(String userName) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userName);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
