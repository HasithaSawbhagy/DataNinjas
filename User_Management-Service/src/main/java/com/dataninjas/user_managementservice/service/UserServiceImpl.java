package com.dataninjas.user_managementservice.service;

import com.dataninjas.user_managementservice.model.User;
import com.dataninjas.user_managementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // Implement user creation logic, including role assignment.
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, User user) {
        // Implement user update logic.
        // Ensure that the user with the given ID exists.
        // Update the user fields accordingly.
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
