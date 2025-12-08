package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.entity.Inventory;
import com.example.inventorymanagementsystem.repository.InventoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getCompleteInventory(){
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryItem(Long id){
        return inventoryRepository.findById(id).orElse(null);
    }
    public void createInventoryItem(Inventory inventory){
        inventoryRepository.save(inventory);
    }
    public boolean deleteInventoryItem(Long id){
        if (!inventoryRepository.existsById(id)){
            return false;
        }
        inventoryRepository.deleteById(id);
        return true;
    }
    public Inventory updateStock(Long id,int change){
        Inventory item = inventoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Item not found!"));
        int newStock = item.getQuantity()+change;
        if (newStock < 0){
            throw new IllegalArgumentException("Stock cannot go below zero!");
        }
        item.setQuantity(newStock);
        return inventoryRepository.save(item);
    }
    public void updateInventoryItem(Inventory updatedInventory, Long id){
        inventoryRepository.findById(id).ifPresent(existingInventory->{
            existingInventory.setName(updatedInventory.getName());
            existingInventory.setPrice(updatedInventory.getPrice());
            existingInventory.setQuantity(updatedInventory.getQuantity());

            inventoryRepository.save(existingInventory);
        });
    }
}
