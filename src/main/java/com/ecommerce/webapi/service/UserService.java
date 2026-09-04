package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.UserRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import java.util.List;

public interface UserService {
    String Create(UserRequest userRequest);
    String update(Long id,UserRequest userRequest);
    String delete(Long id);
    StoreNameResponse findByID(Long id);
    List<StoreNameResponse> findAll();
}
