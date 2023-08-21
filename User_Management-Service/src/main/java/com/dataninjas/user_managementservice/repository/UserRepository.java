package com.dataninjas.user_managementservice.repository;

import com.dataninjas.user_managementservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

}

