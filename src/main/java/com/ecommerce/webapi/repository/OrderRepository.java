package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.Oder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Oder, Long> {
}
