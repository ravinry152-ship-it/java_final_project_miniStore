package com.ecommerce.webapi.service.impl;

import com.ecommerce.webapi.dto.request.OrderRequest;
import com.ecommerce.webapi.model.Oder; // ប្រើឈ្មោះຕາມ Entity របស់អ្នក
import com.ecommerce.webapi.model.User; // สมมติว่ามี User Entity
import com.ecommerce.webapi.repository.OrderRepository;
import com.ecommerce.webapi.repository.UserRepository; // ត្រូវការ UserRepository ដើម្បីទាញយក User
import com.ecommerce.webapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository; // បន្ថែម Repository នេះដើម្បីរក User

    @Override
    @Transactional
    public String Create(OrderRequest orderRequest) {
        // ១. ឆ្កឹះរក User តាមរយៈ userId ដែលផ្ញើមកពី Request
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderRequest.getUserId()));

        // ២. បង្កើត Entity Object និងកំណត់តម្លៃ (Mapping)
        Oder order = new Oder();
        order.setUser(user);
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setStatus("PEND1NG"); // កំណត់ Status ដំបូង (ឧទាហរណ៍: PENDING)
        order.setCreatedAt(LocalDateTime.now()); // កំណត់ម៉ោងបង្កើតស្វ័យប្រវត្តិ

        // ៣. រក្សាទុកចូលក្នុង Database
        orderRepository.save(order);

        return "Order created successfully with ID: " + order.getId();
    }

    @Override
    @Transactional
    public String update(Long id, OrderRequest orderRequest) {
        // ១. ឆ្កឹះរក Order ចាស់
        Oder existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // ២. កែប្រែទិន្នន័យ (ឧទាហរណ៍ កែប្រែ TotalAmount ឬ Status)
        existingOrder.setTotalAmount(orderRequest.getTotalAmount());
        // អាចបន្ថែមការ update status បើមានក្នុង DTO

        // ៣. Save ទុកវិញ
        orderRepository.save(existingOrder);

        return "Order updated successfully with ID: " + id;
    }

    @Override
    @Transactional
    public String delete(Long id) {
        // ១. ពិនិត្យមើលសិនថាតើមាន Order នេះឬអត់
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        // ២. លុបចោល
        orderRepository.deleteById(id);

        return "Order deleted successfully with ID: " + id;
    }

    @Override
    public List<Oder> getMyOrderHistory(String email) {
        return List.of();
    }
}