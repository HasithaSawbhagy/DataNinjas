package com.dataninjas.inventory_management.controller;

import com.dataninjas.inventory_management.exception.ResourceNotFoundException;
import com.dataninjas.inventory_management.model.Inventory;
import com.dataninjas.inventory_management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    // create inventory rest api
    @PostMapping("/inventory")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    //get all inventories
    @GetMapping("/inventory")
    public List<Inventory> getAllEmployees(){
        return inventoryRepository.findAll();
    }

    // get inventory by skuCode rest api
    @GetMapping("/inventory/{skuCode}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long skuCode){
        Inventory inventory = inventoryRepository.findById(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + skuCode ));
        return ResponseEntity.ok(inventory);
    }

    //update inventory rest api
    @PutMapping("/inventory/{skuCode}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long skuCode, @RequestBody Inventory inventoryDetails){
        Inventory inventory = inventoryRepository.findById(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + skuCode ));

        inventory.setSkuCode(inventoryDetails.getSkuCode());
        inventory.setQuantity(inventoryDetails.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    // delete employee rest api
    @DeleteMapping("/inventory/{skuCode}")
    public ResponseEntity <Map<String, Boolean>> deleteInventory(@PathVariable Long skuCode){
        Inventory inventory = inventoryRepository.findById(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + skuCode));
        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put ("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
