package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.CategoryRequest;
import com.ecommerce.webapi.dto.response.CategoryResponse;
import com.ecommerce.webapi.model.Category;
import com.ecommerce.webapi.model.StoreName;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.CategoryRepository;
import com.ecommerce.webapi.repository.StoreNameRepository;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreNameRepository storeNameRepository;
    private final UserRepository userRepository;

    @Override
    public String Create(CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StoreName store = (StoreName) storeNameRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Store not found for this user"));

        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .productImage(categoryRequest.getProductImage())
                .storeName(store)
                .build();

        categoryRepository.save(category);
        return "Category created successfully";
    }

    @Override
    public String update(Long id, CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getStoreName().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this category");
        }

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setProductImage(categoryRequest.getProductImage());
        categoryRepository.save(category);

        return "Category updated successfully";
    }

    @Override
    public String delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getStoreName().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this category");
        }

        categoryRepository.delete(category);
        return "Category deleted successfully";
    }

    @Override
    public CategoryResponse findByID(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .productImage(category.getProductImage())
                .build();
    }
}