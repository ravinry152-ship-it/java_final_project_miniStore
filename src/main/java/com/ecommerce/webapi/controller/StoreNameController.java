package com.ecommerce.webapi.controller;
import com.ecommerce.webapi.dto.request.StoreNameRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import com.ecommerce.webapi.service.StoreNameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.SequencedCollection;

@Slf4j
@RestController
@RequestMapping("/store-name")
@RequiredArgsConstructor
public class StoreNameController {
    @Autowired
    private StoreNameService storeNameService;
    @PostMapping
    public ResponseEntity<Object> create(
            @RequestBody StoreNameRequest storeNameRequest) {

        storeNameService.create(storeNameRequest);

        return ResponseEntity.ok().build();
    }

   @PutMapping("/{id}")
    public ResponseEntity<Object>update(@RequestBody StoreNameRequest storeNameRequest, @PathVariable Long id){
        log.info("update store name");
        storeNameService.update(id, storeNameRequest);
        return ResponseEntity.ok().build();
   }

    @GetMapping
    public ResponseEntity<SequencedCollection<StoreNameResponse>> getAllBooks() {
        log.info("Get all book");
        SequencedCollection<StoreNameResponse> store = storeNameService.findAll();
        return ResponseEntity.ok(store);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreNameResponse> getBookById(@PathVariable Long id) {
        log.info("Get todo by id: {}", id);
        StoreNameResponse todoResponse = storeNameService.findByID(id);
        return ResponseEntity.ok(todoResponse);
    }
}

