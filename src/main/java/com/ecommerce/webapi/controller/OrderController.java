package com.ecommerce.webapi.controller;

import com.ecommerce.webapi.dto.request.OrderRequest;
import com.ecommerce.webapi.model.Oder;
import com.ecommerce.webapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST: បង្កើត Order ថ្មី
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        String result = orderService.Create(orderRequest);
        return ResponseEntity.ok(result);
    }

    // GET: មើលប្រវត្តិការបញ្ជាទិញរបស់ User ដែលកំពុង Login ស្រាប់ (តាមរយៈ Token/Principal)
    @GetMapping("/history")
    public ResponseEntity<List<Oder>> getMyOrderHistory(Principal principal) {
        // principal.getName() នឹងយក Email ឬ Username ពី JWT មកប្រើប្រាស់
        String email = principal.getName();
        List<Oder> history = orderService.getMyOrderHistory(email);
        return ResponseEntity.ok(history);
    }

    // PUT: កែប្រែ Order តាមរយៈ ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        String result = orderService.update(id, orderRequest);
        return ResponseEntity.ok(result);
    }

    // DELETE: លុប Order តាមរយៈ ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        String result = orderService.delete(id);
        return ResponseEntity.ok(result);
    }
}