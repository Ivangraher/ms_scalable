package com.example.ms_inventory.repository;

import com.example.ms_inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySku(String sku);
    List<Inventory> findBySkuIn(List<String> list);
}
