package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
