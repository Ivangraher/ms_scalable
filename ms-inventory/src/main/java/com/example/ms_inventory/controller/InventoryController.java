package com.example.ms_inventory.controller;

import com.example.ms_inventory.dto.OrderItemRequest;
import com.example.ms_inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku") String sku){
        return service.isInStock(sku);
    }

    /*@PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public void areInStock(@RequestBody List<OrderItemRequest> orderItems) {
        service.validateStock(orderItems);
    }*/

    @PostMapping("/in-stock")
    public ResponseEntity<Boolean> checkStock(@RequestBody List<OrderItemRequest> orderItems) {
        try {
            service.validateStock(orderItems); // Esto lanza una excepción si algo va mal
            return ResponseEntity.ok(true);  // Stock disponible
        } catch (ResponseStatusException ex) {
            return ResponseEntity.badRequest().body(false);  // Error, stock no disponible
        }
    }


    /*@PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<String> areInStock(@RequestBody List<OrderItemRequest> orderItems) {
        List<String> errores = service.areInStock(orderItems);

        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", errores));
        }

        return List.of("Todos los productos tienen stock suficiente");
    }*/

}
