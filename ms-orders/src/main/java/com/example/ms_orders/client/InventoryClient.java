package com.example.ms_orders.client;

import com.example.ms_orders.dto.OrderItemRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryClient {

    private final WebClient webClient;

    @Autowired
    public InventoryClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("lb://ms-inventory/api/inventory")
                .build();
    }


    public boolean checkStock(List<OrderItemRequest> orderItems) {
        log.info("Verificando inventario con los items: {}", orderItems);

        try {
            // Realizamos la llamada al endpoint /in-stock de ms-inventory
            Boolean stockAvailable = webClient.post()
                    .uri("/in-stock")
                    .bodyValue(orderItems)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block(); // Bloquea la ejecución hasta recibir la respuesta

            log.info("Respuesta de ms-inventory: {}", stockAvailable);

            return stockAvailable != null && stockAvailable; // Verificamos si el stock está disponible
        } catch (WebClientResponseException ex) {
            log.error("Error en la llamada a ms-inventory: {}", ex.getResponseBodyAsString());
            throw new RuntimeException("Error en la verificación de inventario", ex);
        }
    }


    public void updateStock(List<OrderItemRequest> orderItems) {
        try {
            // Llamada POST para actualizar el stock
            webClient.post()
                    .uri("/update-stock") // El endpoint para actualizar el stock
                    .bodyValue(orderItems)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Stock actualizado correctamente para los items: {}", orderItems);
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("Error al actualizar el inventario", ex);
        }
    }


}

