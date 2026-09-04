package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.OderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OderItem, Long> {
}
