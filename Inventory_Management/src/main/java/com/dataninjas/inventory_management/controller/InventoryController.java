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
    @PostMapping("/post")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    //get all inventories
    @GetMapping("/get")
    public List<Inventory> getAllEmployees(){
        return inventoryRepository.findAll();
    }

    // get inventory by skuCode rest api
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id ));
        return ResponseEntity.ok(inventory);
    }

    //update inventory rest api
    @PutMapping("/put/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id ));

        inventory.setSkuCode(inventoryDetails.getSkuCode());
        inventory.setQuantity(inventoryDetails.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    // delete employee rest api
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Map<String, Boolean>> deleteInventory(@PathVariable Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not exist with skuCode: " + id));
        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put ("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
