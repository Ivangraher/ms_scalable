package com.example.ms_orders.service;

import com.example.ms_orders.client.InventoryClient;
import com.example.ms_orders.dto.OrderRequest;
import com.example.ms_orders.dto.OrderResponse;
import com.example.ms_orders.events.OrderEvent;
import com.example.ms_orders.mapper.OrderMapper;
import com.example.ms_orders.model.Order;
import com.example.ms_orders.model.OrderItem;
import com.example.ms_orders.model.OrderStatus;
import com.example.ms_orders.repository.OrderRepository;
import com.example.ms_orders.utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final WebClient.Builder client;

    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, String> template;

    private final ObservationRegistry observationRegistry;


    public OrderResponse createOrder(OrderRequest orderRequest) {

        // Observa la consulta al inventario
        boolean isInStock = Boolean.TRUE.equals(Observation.createNotStarted("inventory-stock-check", observationRegistry)
                .observe(() -> inventoryClient.checkStock(orderRequest.getOrderItems())));

        if (!isInStock) {
            throw new IllegalArgumentException("Some of the products aren't in stock");
        }

        // Observa la creación de la orden y guardado en la base de datos
        Order order = Observation.createNotStarted("order-persistence", observationRegistry)
                .observe(() -> {
                    Order newOrder = Order.builder()
                            .orderNumber(UUID.randomUUID().toString())
                            .orderItems(orderRequest.getOrderItems().stream()
                                    .map(item -> mapper.toEntity(item, null))
                                    .collect(Collectors.toList()))
                            .build();

                    newOrder.getOrderItems().forEach(item -> item.setOrder(newOrder));
                    repository.save(newOrder);
                    return newOrder;
                });

        // Observa la publicación del evento a Kafka
        Observation.createNotStarted("order-kafka-publish", observationRegistry)
                .observe(() -> {
                    template.send("orders-topic", JsonUtils.toJson(
                            new OrderEvent(order.getOrderNumber(), order.getOrderItems().size(), OrderStatus.CREATED)
                    ));
                    return null;
                });

        // Observa la actualización de stock
        Observation.createNotStarted("inventory-update-stock", observationRegistry)
                .observe(() -> {
                    inventoryClient.updateStock(orderRequest.getOrderItems());
                    return null;
                });

        return mapper.toResponse(order);
    }



    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

}
