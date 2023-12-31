package com.dataninjas.inventory_management.service;

import com.dataninjas.inventory_management.dto.InventoryResponse;
import com.dataninjas.inventory_management.exception.ResourceNotFoundException;
import com.dataninjas.inventory_management.model.Inventory;
import com.dataninjas.inventory_management.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
//        System.out.println("SKU Codes: " + skuCode);

        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCode);
//        System.out.println("Inventory List Size: " + inventoryList.size());

        List<InventoryResponse> inventoryResponses = inventoryList.stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();

        // Print the output for debugging
        System.out.println("Inventory Response: " + inventoryResponses);

        return inventoryResponses;
    }


    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllEmployees() {
        return inventoryRepository.findAll();
    }

    public ResponseEntity<Inventory> getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id));
        return ResponseEntity.ok(inventory);
    }

    public ResponseEntity<Inventory> updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id));

        if (inventoryDetails.getSkuCode() != null)
            inventory.setSkuCode(inventoryDetails.getSkuCode());
        if (inventoryDetails.getQuantity() >= 0)
            inventory.setQuantity(inventoryDetails.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    public ResponseEntity<Map<String, Boolean>> deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id));
        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
