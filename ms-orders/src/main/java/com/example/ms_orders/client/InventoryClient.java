package com.example.ms_orders.client;

import com.example.ms_orders.dto.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryClient {

    private final WebClient webClient;

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


}

