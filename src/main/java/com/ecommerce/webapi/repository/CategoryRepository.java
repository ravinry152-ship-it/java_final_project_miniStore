package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
