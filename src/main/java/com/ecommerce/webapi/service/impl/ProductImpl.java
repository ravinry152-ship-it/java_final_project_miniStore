package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.ProductRequest;
import com.ecommerce.webapi.dto.response.ProductResponse;
import com.ecommerce.webapi.model.Category;
import com.ecommerce.webapi.model.Product;
import com.ecommerce.webapi.model.StoreName;
import com.ecommerce.webapi.model.User;
import com.ecommerce.webapi.repository.CategoryRepository;
import com.ecommerce.webapi.repository.ProductRepository;
import com.ecommerce.webapi.repository.StoreNameRepository;
import com.ecommerce.webapi.repository.UserRepository;
import com.ecommerce.webapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreNameRepository storeNameRepository;
    private final UserRepository userRepository;

    @Override
    public String Create(ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StoreName store = (StoreName) storeNameRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Store not found for this user"));

        Category category = categoryRepository.findById(productRequest.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
       // product.setStoreName(store);
        product.setCategory(category);
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setProductImage(productRequest.getProductImage());
        product.setStock(productRequest.getStock());
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        return "Product created successfully";
    }

    @Override
    public String update(Long id, ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStoreName() == null || !product.getStoreName().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this product");
        }

        Category category = categoryRepository.findById(productRequest.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setProductImage(productRequest.getProductImage());
        product.setStock(productRequest.getStock());
        productRepository.save(product);

        return "Product updated successfully";
    }

    @Override
    public String delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStoreName() == null || !product.getStoreName().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this product");
        }

        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public ProductResponse findByID(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .storeId(product.getStoreName() != null ? product.getStoreName().getId() : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .productName(product.getProductName())
                .price(product.getPrice())
                .productImage(product.getProductImage())
                .stock(product.getStock())
                .build();
    }
}