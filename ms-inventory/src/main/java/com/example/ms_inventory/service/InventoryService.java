package com.example.ms_inventory.service;

import com.example.ms_inventory.dto.OrderItemRequest;
import com.example.ms_inventory.model.Inventory;
import com.example.ms_inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepository repository;


    public boolean isInStock(String sku) {
        var stock = repository.findBySku(sku);
        return stock.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public void validateStock(List<OrderItemRequest> orderItems) {
        Map<String, Integer> inventoryMap = repository.findBySkuIn(
                orderItems.stream().map(OrderItemRequest::getSku).toList()
        ).stream().collect(Collectors.toMap(Inventory::getSku, Inventory::getQuantity));

        orderItems.forEach(item -> {
            if (inventoryMap.containsKey(item.getSku())) {
                int stockDisponible = inventoryMap.get(item.getSku());
                System.out.println("SKU encontrado: " + item.getSku() + " - Stock disponible: " + stockDisponible);
            } else {
                System.out.println("SKU no encontrado: " + item.getSku());
            }
        });

        List<String> errores = orderItems.stream()
                .filter(item -> !inventoryMap.containsKey(item.getSku())
                        || inventoryMap.get(item.getSku()) < item.getQuantity())
                .map(item -> inventoryMap.containsKey(item.getSku())
                        ? "Producto con SKU " + item.getSku() + " tiene cantidad insuficiente"
                        : "Producto con SKU " + item.getSku() + " no existe")
                .toList();

        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Errores en el inventario: " + String.join(", ", errores));
        }
    }


    /*public List<String> areInStock(List<OrderItemRequest> orderItems) {

        // Convertir inventario a un Map para búsqueda rápida O(1)
        Map<String, Integer> inventoryMap = repository.findBySkuIn(
                        orderItems.stream()
                                .map(OrderItemRequest::getSku)
                                .toList()
                ).stream()
                .collect(Collectors.toMap(Inventory::getSku, Inventory::getQuantity));

        // Validar inventario
        return orderItems.stream()
                .filter(item -> !inventoryMap.containsKey(item.getSku())
                        || inventoryMap.get(item.getSku()) < item.getQuantity())
                .map(item -> inventoryMap.containsKey(item.getSku())
                        ? "Producto con SKU " + item.getSku() + " tiene cantidad insuficiente"
                        : "Producto con SKU " + item.getSku() + " no existe")
                .toList();
    }*/

}
