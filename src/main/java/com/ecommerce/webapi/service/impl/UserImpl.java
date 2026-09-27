package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.UserRequest;
import com.ecommerce.webapi.dto.response.UserResponse;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String Create(UserRequest userRequest) {

        User user = User.builder()
                .name(userRequest.getUserName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        userRepository.save(user);

        log.info("User created successfully: {}", user.getEmail());

        return "User created successfully";
    }

    @Override
    public String update(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + id
                        )
                );

        user.setName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());

        if (userRequest.getPassword() != null
                && !userRequest.getPassword().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(
                            userRequest.getPassword()
                    )
            );
        }

        userRepository.save(user);

        log.info("User updated successfully: {}", id);

        return "User updated successfully";
    }

    @Override
    public String delete(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException(
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully: {}", id);

        return "User deleted successfully";
    }

    @Override
    public UserResponse findByID(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + id
                        )
                );

        UserResponse response = new UserResponse();

        // ⭐ IMPORTANT
        response.setId(user.getId());

        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());

        // មិនបញ្ជូន password ទៅ frontend
        // response.setPassword(user.getPassword());

        return response;
    }

    @Override
    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    UserResponse response = new UserResponse();

                    response.setId(user.getId());

                    response.setUserName(user.getUsername());

                    response.setEmail(user.getEmail());

                    // មិនបញ្ជូន password
                    // response.setPassword(user.getPassword());

                    return response;
                })
                .collect(Collectors.toList());
    }
}