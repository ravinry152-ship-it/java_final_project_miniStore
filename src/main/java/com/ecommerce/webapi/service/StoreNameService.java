package com.ecommerce.webapi.service;

import com.ecommerce.webapi.dto.request.StoreNameRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;

import java.util.SequencedCollection;

public interface StoreNameService {

    String Create(StoreNameRequest storeNameRequest);

    // Create store for logged-in user
    String create(StoreNameRequest storeNameRequest);

    // Update store
    String update(Long id, StoreNameRequest storeNameRequest);

    // Delete store
    String delete(Long id);

    // Get store by ID
    StoreNameResponse findByID(Long id);

    // Get all stores
    SequencedCollection<StoreNameResponse> findAll();
}