package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.OrderRequest;
import com.ecommerce.webapi.model.Oder;

import java.util.List;

public interface OrderService {

    String Create(OrderRequest orderRequest);
    String update(Long id, OrderRequest orderRequest);
    String delete(Long id);
    List<Oder> getMyOrderHistory(String email);
}
