package com.ecommerce.webapi.controller;

import com.ecommerce.webapi.dto.request.LoginRequest;
import com.ecommerce.webapi.dto.request.SignupRequest;
import com.ecommerce.webapi.dto.response.AuthResponse;
import com.ecommerce.webapi.dto.response.UserResponse;
import com.ecommerce.webapi.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(
            @RequestBody SignupRequest request) {

        return ResponseEntity.ok(
                authService.signup(request)
        );
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    // =========================
    // GET CURRENT USER PROFILE (/me)
    // =========================
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserProfile(Principal principal) {
        // យក Email របស់ User ដែលផ្ទៀងផ្ទាត់រួចតាមរយៈ Token (JWT)
        String email = principal.getName();

        // ហៅ Service ដើម្បីទាញយកទិន្នន័យ User មកវិញ
        UserResponse userResponse = authService.getUserProfileByEmail(email);

        return ResponseEntity.ok(userResponse);
    }
}