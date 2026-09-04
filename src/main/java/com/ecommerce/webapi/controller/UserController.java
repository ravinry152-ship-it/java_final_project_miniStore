package com.ecommerce.webapi.controller;
import com.ecommerce.webapi.dto.request.UserRequest;
import com.ecommerce.webapi.dto.response.StoreNameResponse;
import com.ecommerce.webapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String Create(@RequestBody UserRequest userRequest) {
        return userService.Create(userRequest);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/{id}")
    public StoreNameResponse findByID(@PathVariable Long id) {
        return userService.findByID(id);
    }

    @GetMapping
    public List<StoreNameResponse> findAll() {
        return userService.findAll();
    }
}
