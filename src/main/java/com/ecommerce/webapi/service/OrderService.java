package com.ecommerce.webapi.service;
import com.ecommerce.webapi.dto.request.OrderRequest;
public interface OrderService {

    String Create(OrderRequest orderRequest);
    String update(Long id, OrderRequest orderRequest);
    String delete(Long id);
}
