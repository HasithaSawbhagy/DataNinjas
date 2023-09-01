package com.dataninjas.inventory_management.controller;

import com.dataninjas.inventory_management.exception.ResourceNotFoundException;
import com.dataninjas.inventory_management.model.Inventory;
import com.dataninjas.inventory_management.repository.InventoryRepository;
import com.dataninjas.inventory_management.service.InventoryService;
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
    @Autowired
    private InventoryService inventoryService;

    // create inventory rest api
    @PostMapping("/post")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    //get all inventories
    @GetMapping("/get")
    public List<Inventory> getAllEmployees(){
        return inventoryService.getAllEmployees();
    }

    // get inventory by skuCode rest api
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id){
        return inventoryService.getInventoryById(id);
    }

    //update inventory rest api
    @PutMapping("/put/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails){
        return inventoryService.updateInventory(id,inventoryDetails);
    }

    // delete employee rest api
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Map<String, Boolean>> deleteInventory(@PathVariable Long id){
        return inventoryService.deleteInventory(id);
    }
}
