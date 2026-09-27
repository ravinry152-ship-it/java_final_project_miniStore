package com.ecommerce.webapi.controller;

import com.ecommerce.webapi.dto.request.ProductRequest;
import com.ecommerce.webapi.dto.response.ProductResponse;
import com.ecommerce.webapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create Product
    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody ProductRequest productRequest
    ) {
        String result = productService.Create(productRequest);
        return ResponseEntity.ok(result);
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest
    ) {
        String result = productService.update(id, productRequest);
        return ResponseEntity.ok(result);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {
        String result = productService.delete(id);
        return ResponseEntity.ok(result);
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable Long id
    ) {
        ProductResponse response = productService.findByID(id);
        return ResponseEntity.ok(response);
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(required = false) String product_name
    ) {
        List<ProductResponse> responses;

        if (product_name != null && !product_name.trim().isEmpty()) {
            // ហៅ Service សម្រាប់ស្វែងរកតាមឈ្មោះ (ឧទាហរណ៍៖ findByProductNameContainingIgnoreCase)
            responses = productService.findByName(product_name.trim());
        } else {
            // បើគ្មានការ Search ទេ ទាញយកទាំងអស់
            responses = productService.findAll();
        }

        return ResponseEntity.ok(responses);
    }

    // Get Products by Category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getByCategory(
            @PathVariable Long categoryId
    ) {

        List<ProductResponse> responses =
                productService.findByCategoryId(categoryId);

        return ResponseEntity.ok(responses);
    }
}