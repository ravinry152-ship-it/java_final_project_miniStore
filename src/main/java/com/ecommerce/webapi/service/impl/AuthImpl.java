package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.LoginRequest;
import com.ecommerce.webapi.dto.request.SignupRequest;
import com.ecommerce.webapi.dto.response.AuthResponse;
import com.ecommerce.webapi.dto.response.UserResponse;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.security.JwtService;
import com.ecommerce.webapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // ==========Sign up===============
    @Override
    public Map<String, String> signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        // បង្កើត Map ដើម្បីផ្ញើចេញមកជា JSON
        Map<String, String> response = new HashMap<>();
        response.put("message", "Signup successfully");
        return response;
    }

    // =========================
    // LOGIN
    // =========================
    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public UserResponse getUserProfileByEmail(String email) {
        return null;
    }
}