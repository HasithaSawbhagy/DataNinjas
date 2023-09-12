package com.DataNinjas.Order.management.service;

import com.DataNinjas.Order.management.Exception.ResourceNotFoundException;
import com.DataNinjas.Order.management.dto.InventoryResponse;
import com.DataNinjas.Order.management.dto.OrderLineItemsDto;
import com.DataNinjas.Order.management.dto.OrderRequest;
import com.DataNinjas.Order.management.model.Order;
import com.DataNinjas.Order.management.model.OrderLineItems;
import com.DataNinjas.Order.management.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //call and place order if product exists
        //stock
        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        // Log or print the inventory response for debugging
        System.out.println(Arrays.toString(inventoryResponsesArray));

        assert inventoryResponsesArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            System.out.println(allProductsInStock);
        } else {
            throw new IllegalArgumentException("Product is not in stock!");
        }


    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    public List<Order> getOrder (){
        return orderRepository.findAll();
    }

    public ResponseEntity<Order> updateOrder (@PathVariable Long id, @RequestBody Order order){
        Order orderItem = orderRepository.findById(id) .orElseThrow(()->new ResourceNotFoundException("Reception not exist with Id:"+id));
        orderItem.setId(order.getId());
        orderItem.setOrderNumber(order.getOrderNumber());
        orderItem.setOrderLineItemsList(order.getOrderLineItemsList());
        Order updatedOrder = orderRepository.save(orderItem);
        return ResponseEntity.ok(updatedOrder);
    }

    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id){
        Order orderItem = orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Reception not exist with Id:"+id));
        orderRepository.delete(orderItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
