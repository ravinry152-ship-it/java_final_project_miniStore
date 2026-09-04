package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.StoreNameRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;

import java.util.List;

public interface StoreNameService {
    String Create(StoreNameRequest storeNameRequest);
    String update(Long id, StoreNameRequest storeNameRequest);
    String delete(Long id);
    StoreNameResponse findByID(Long id);
    List<StoreNameResponse> findAll();
}
