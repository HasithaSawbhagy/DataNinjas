package com.example.order.Controller;

import com.example.order.Exception.ResourceNotFoundException;
import com.example.order.Model.Order;
import com.example.order.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    //Get all receptions
    @GetMapping("/order")
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    //Create receptions rest API
    @PostMapping("/order")
    public Order createOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }

    //Update reception rest api
    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails){
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not exist with ID: "+id));
        order.setId(orderDetails.getId());
        order.setName(orderDetails.getName());
        Order updateOrder = orderRepository.save(order);
        return ResponseEntity.ok(updateOrder);
    }

    //Delete reception rest api
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id){

        Order order = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not exist with ID: "+id));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
