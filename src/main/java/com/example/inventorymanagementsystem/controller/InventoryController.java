package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.entity.Inventory;
import com.example.inventorymanagementsystem.repository.InventoryRepository;
import com.example.inventorymanagementsystem.service.InventoryService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
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
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam int quantityChange){
        try{
            Inventory updatedItem = inventoryService.updateStock(id, quantityChange);
            return  ResponseEntity.ok(updatedItem);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(RuntimeException e ){
            return ResponseEntity.status(400).body(e.getMessage());
        }
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
        boolean deleted = inventoryService.deleteInventoryItem(id);
        return deleted ? ResponseEntity.ok("Item deleted.") : ResponseEntity.status(404).body("Item not found.");
    }
}
