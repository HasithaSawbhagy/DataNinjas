package com.dataninjas.inventory_management.service;

import com.dataninjas.inventory_management.exception.ResourceNotFoundException;
import com.dataninjas.inventory_management.model.Inventory;
import com.dataninjas.inventory_management.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public Inventory createInventory(Inventory inventory){
        return inventoryRepository.save(inventory);
    }
    public List<Inventory> getAllEmployees(){
        return inventoryRepository.findAll();
    }
    public ResponseEntity<Inventory> getInventoryById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with id: " + id ));
        return ResponseEntity.ok(inventory);
    }
    public ResponseEntity<Inventory> updateInventory(Long id, Inventory inventoryDetails){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with id: " + id ));

        if(inventoryDetails.getSkuCode() != null)
            inventory.setSkuCode(inventoryDetails.getSkuCode());
        if(inventoryDetails.getQuantity() >= 0)
            inventory.setQuantity(inventoryDetails.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }
    public ResponseEntity <Map<String, Boolean>> deleteInventory(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with id: " + id));
        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put ("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
