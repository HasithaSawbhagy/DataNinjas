package com.DataNinjas.Order.management.repository;

import com.DataNinjas.Order.management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
