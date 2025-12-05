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
    public void deleteInventoryItem(Long id){
        inventoryRepository.deleteById(id);
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
