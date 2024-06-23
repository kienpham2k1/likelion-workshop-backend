package org.example.likelion.service.auth;

import org.example.likelion.dto.auth.UserDetailsImpl;
import org.example.likelion.dto.mapper.IUserMapper;
import org.example.likelion.dto.request.LoginRequest;
import org.example.likelion.dto.request.UserRegisterRequest;
import org.example.likelion.dto.response.JwtResponse;
import org.example.likelion.dto.response.UserRegisterResponse;
import org.example.likelion.exception.DuplicateRecordException;
import org.example.likelion.model.User;
import org.example.likelion.repository.UserRepository;
import org.example.likelion.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserRegisterResponse register(UserRegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateRecordException("Error: Username is already taken!");
        }
        User user = IUserMapper.INSTANCE.toEntity(registerRequest);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        return IUserMapper.INSTANCE.toDtoRegisterResponse(userRepository.save(user));
    }

    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse saveUserToken() {
        return null;
    }

    @Override
    public void revokeAllUserTokens() {

    }

    @Override
    public void refreshToken() {

    }
}
