package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.StoreName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreNameRepository  extends JpaRepository<StoreName, Long> {
}
