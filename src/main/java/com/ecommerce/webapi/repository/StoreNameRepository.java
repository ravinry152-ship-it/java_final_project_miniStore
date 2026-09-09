package com.ecommerce.webapi.repository;

import com.ecommerce.webapi.model.StoreName;
import com.ecommerce.webapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreNameRepository  extends JpaRepository<StoreName, Long> {
    Optional<Object> findByUser(User currentUser);
}
