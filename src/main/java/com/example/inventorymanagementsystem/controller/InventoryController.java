package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.entity.Inventory;
import com.example.inventorymanagementsystem.repository.InventoryRepository;
import com.example.inventorymanagementsystem.service.InventoryService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return ResponseEntity.ok(inventoryService.getCompleteInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getItem(@PathVariable Long id){
        Inventory item = inventoryService.getInventoryItem(id);
        return (item != null) ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Inventory> createItem(@RequestBody Inventory inventory){
        inventoryService.createInventoryItem(inventory);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@RequestBody Inventory updatedInventory, @PathVariable Long id){
        Inventory existing = inventoryService.getInventoryItem(id);
        if (existing == null) {
            return ResponseEntity.status(404).body("Inventory item not found.");
        }
        inventoryService.updateInventoryItem(updatedInventory,id);
        return ResponseEntity.ok("Inventory item updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        Inventory existing = inventoryService.getInventoryItem(id);
        if(existing == null){
            return ResponseEntity.status(404).body("Inventory item not found.");
        }
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.ok("Inventory item deleted!");
    }
}
