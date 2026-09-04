package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.UserRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String Create(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUserName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
        userRepository.save(user);
        log.info("User created successfully: {}", user.getEmail());
        return "User created successfully";
    }

    @Override
    public String update(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        userRepository.save(user);
        log.info("User updated successfully: {}", id);
        return "User updated successfully";
    }

    @Override
    public String delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully: {}", id);
        return "User deleted successfully";
    }

    @Override
    public StoreNameResponse findByID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return StoreNameResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .storeName(user.getStore() != null ? user.getStore().getStoreName() : null)
                .build();
    }

    @Override
    public List<StoreNameResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> StoreNameResponse.builder()
                        .id(user.getId())
                        .userName(user.getUsername())
                        .storeName(user.getStore() != null ? user.getStore().getStoreName() : null)
                        .build())
                .collect(Collectors.toList());
    }
}