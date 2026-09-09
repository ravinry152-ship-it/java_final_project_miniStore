package com.ecommerce.webapi.service;

import com.ecommerce.webapi.dto.request.LoginRequest;
import com.ecommerce.webapi.dto.request.SignupRequest;
import com.ecommerce.webapi.dto.response.AuthResponse;
import com.ecommerce.webapi.dto.response.UserResponse;

import java.util.Map;

public interface AuthService {
    Map<String, String> signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getUserProfileByEmail(String email);
}