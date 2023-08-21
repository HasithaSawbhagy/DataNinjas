package com.dataninjas.user_managementservice.service;

import com.dataninjas.user_managementservice.model.User;

public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);
    User getUserById(String id);
    User getUserByUsername(String username);
    void deleteUser(String id);
}
