package com.dataninjas.user_managementservice.service;

import com.dataninjas.user_managementservice.Exception.NotFoundException;
import com.dataninjas.user_managementservice.model.User;
import com.dataninjas.user_managementservice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void save(User user) {
        // Implement user creation logic, including role assignment.
         userRepository.insert(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }


    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User is not found"));
        existingUser.setId(user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setTelephone(user.getTelephone());
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }






}
