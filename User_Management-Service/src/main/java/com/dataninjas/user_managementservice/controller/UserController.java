package com.dataninjas.user_managementservice.controller;

import com.dataninjas.user_managementservice.Exception.NotFoundException;
import com.dataninjas.user_managementservice.model.User;
import com.dataninjas.user_managementservice.repository.UserRepository;
import com.dataninjas.user_managementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class UserController {
    // Implement CRUD operations for customer users

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/addUser")
    public String createUser(@RequestBody User user) {
         userService.save(user);
         return "saved";
    }

    @GetMapping("/viewUser")
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    @GetMapping("/viewUser/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User is not found with id " + id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id,user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id) {
        if(!userRepository.existsById(id)){
            throw new NotFoundException(("User not found with id " + id));
        }
        userRepository.deleteById(id);
        return "User with id " + id + "has been deleted";
    }
}