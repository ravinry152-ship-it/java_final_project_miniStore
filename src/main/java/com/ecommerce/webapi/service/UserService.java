package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.UserRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import com.ecommerce.webapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    String Create(UserRequest userRequest);
    String update(Long id,UserRequest userRequest);
    String delete(Long id);
    UserResponse findByID(Long id);
    List<UserResponse> findAll();
}
