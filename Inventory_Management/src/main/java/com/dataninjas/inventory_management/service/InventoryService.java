package com.dataninjas.inventory_management.service;

import com.dataninjas.inventory_management.exception.ResourceNotFoundException;
import com.dataninjas.inventory_management.model.Inventory;
import com.dataninjas.inventory_management.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id ));
        return ResponseEntity.ok(inventory);
    }
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id ));

        if(inventoryDetails.getSkuCode() != null)
            inventory.setSkuCode(inventoryDetails.getSkuCode());
        if(inventoryDetails.getQuantity() >= 0)
            inventory.setQuantity(inventoryDetails.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }
    public ResponseEntity <Map<String, Boolean>> deleteInventory(@PathVariable Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id));
        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put ("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
