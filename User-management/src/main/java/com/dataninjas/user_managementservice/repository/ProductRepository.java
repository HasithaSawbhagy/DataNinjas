package com.dataninjas.user_managementservice.repository;

import com.dataninjas.user_managementservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
