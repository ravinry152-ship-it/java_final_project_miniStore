package com.ecommerce.webapi.service;

import com.ecommerce.webapi.dto.response.OrderItemResponse;

import java.util.List;

public interface OrderItemService {
    OrderItemResponse findByID(Long id);
    List<OrderItemResponse> findAll();
}
