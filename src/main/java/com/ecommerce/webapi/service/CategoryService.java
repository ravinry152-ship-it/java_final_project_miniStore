package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.CategoryRequest;
import com.ecommerce.webapi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    String Create(CategoryRequest categoryRequest);
    String update(Long id, CategoryRequest categoryRequest);
    String delete(Long id);
    CategoryResponse findByID(Long id);
    List<CategoryResponse> findAll();
}
