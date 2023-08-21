package com.dataninjas.user_managementservice.controller;

import com.dataninjas.user_managementservice.model.User;
import com.dataninjas.user_managementservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    // Implement CRUD operations for customer users

    private UserRepository userRepository;


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String username) {
        userRepository.deleteById(username);
    }
}