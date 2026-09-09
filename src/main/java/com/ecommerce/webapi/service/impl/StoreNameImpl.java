package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.StoreNameRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import com.ecommerce.webapi.model.StoreName;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.StoreNameRepository;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.service.StoreNameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreNameImpl implements StoreNameService {

    private final StoreNameRepository storeNameRepository;
    private final UserRepository userRepository;

    @Override
    public String Create(StoreNameRequest storeNameRequest) {
        return create(storeNameRequest);
    }

    @Override
    public String create(StoreNameRequest storeNameRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // បង្កើត Store ថ្មីជានិច្ច មិនបាច់ស្វែងរក Store ចាស់មក update ទេ
        StoreName storeName = new StoreName();

        storeName.setStoreName(storeNameRequest.getStoreName());
        storeName.setDescription(storeNameRequest.getDescription());
        storeName.setUser(currentUser);
        storeName.setCreatedAt(LocalDateTime.now());

        storeNameRepository.save(storeName);

        return "Create store successfully";
    }

    @Override
    public String update(Long id, StoreNameRequest storeNameRequest) {
        // 1. យក User បច្ចុប្បន្នដែលកំពុង Logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. ស្វែងរក Store តាម ID
        StoreName storeName = storeNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store name not found"));

        // 3. ផ្ទៀងផ្ទាត់ថា Store នេះជារបស់ User បច្ចុប្បន្នមែនឬអត់
        if (!storeName.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this store");
        }

        storeName.setStoreName(storeNameRequest.getStoreName());
        storeName.setDescription(storeNameRequest.getDescription());
        storeNameRepository.save(storeName);

        return "Update store successfully";
    }

    @Override
    public String delete(Long id) {
        // 1. យក User បច្ចុប្បន្នដែលកំពុង Logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. ស្វែងរក Store តាម ID
        StoreName storeName = storeNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store name not found"));

        // 3. ផ្ទៀងផ្ទាត់ថា Store នេះជារបស់ User បច្ចុប្បន្នមែនឬអត់
        if (!storeName.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this store");
        }

        storeNameRepository.delete(storeName);
        return "Delete store successfully";
    }

    @Override
    public StoreNameResponse findByID(Long id) {
        StoreName storeName = storeNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store name not found"));

        return mapToResponse(storeName);
    }

    @Override
    public SequencedCollection<StoreNameResponse> findAll() {
        return storeNameRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()).reversed();
    }

    private StoreNameResponse mapToResponse(StoreName storeName) {
        return StoreNameResponse.builder()
                .id(storeName.getId())
                .storeName(storeName.getStoreName())
                .description(storeName.getDescription())
                .build();
    }
}