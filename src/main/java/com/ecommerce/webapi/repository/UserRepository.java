package com.ecommerce.webapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.webapi.model.User;


public interface UserRepository  extends JpaRepository<User, Long> {
}
