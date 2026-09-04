package com.ecommerce.webapi.service;

import com.ecommerce.webapi.dto.request.ProductRequest;

import com.ecommerce.webapi.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    String Create(ProductRequest productRequest);
    String update(Long id, ProductRequest productRequest);
    String delete(Long id);
    ProductResponse findByID(Long id);
    List<ProductResponse> findAll();
}
