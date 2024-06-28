package org.example.likelion.service.implService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.mapper.IAdminMapper;
import org.example.likelion.dto.request.AdminRequest;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.response.AdminResponse;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.enums.TokenType;
import org.example.likelion.exception.DuplicateRecordException;
import org.example.likelion.model.Admin;
import org.example.likelion.model.Token;
import org.example.likelion.repository.AdminRepository;
import org.example.likelion.repository.TokenRepository;
import org.example.likelion.service.AdminService;
import org.example.likelion.service.jwt.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AdminResponse create(AdminRequest rq) {
        if (adminRepository.findByUsername(rq.getUsername()).isPresent())
            throw new DuplicateRecordException(ErrorMessage.ADMIN_USERNAME_HAS_TAKEN);
        Admin admin = IAdminMapper.INSTANCE.toEntity(rq);
        Admin saveAdmin = adminRepository.save(admin);
        admin.setPassword(encoder.encode(rq.getPassword()));

        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.generateRefreshToken(admin);
        saveUserToken(saveAdmin, jwtToken);
        return IAdminMapper.INSTANCE.toDto(admin);

    }
    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new JwtResponse(accessToken, refreshToken);
    }  @Override
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
            var user = this.adminRepository.findByUsername(username)
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
    public void saveUserToken(UserDetailsImpl userDetails, String jwtToken) {
        var token = Token.builder()
                .admin(IAdminMapper.INSTANCE.toEntity(userDetails))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
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
}
