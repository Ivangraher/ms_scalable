package com.example.ms_orders.service;

import com.example.ms_orders.client.InventoryClient;
import com.example.ms_orders.dto.OrderRequest;
import com.example.ms_orders.dto.OrderResponse;
import com.example.ms_orders.mapper.OrderMapper;
import com.example.ms_orders.model.Order;
import com.example.ms_orders.model.OrderItem;
import com.example.ms_orders.repository.OrderRepository;
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

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private WebClient.Builder client;

    @Autowired
    private InventoryClient inventoryClient;


    public OrderResponse createOrder(OrderRequest orderRequest) {

        boolean isInStock = inventoryClient.checkStock(orderRequest.getOrderItems());

        if (!isInStock) {
            throw new IllegalArgumentException("Uno o más productos no están en stock");
        }

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItems(orderRequest.getOrderItems().stream()
                        .map(item -> mapper.toEntity(item, null))
                        .collect(Collectors.toList()))
                .build();

        order.getOrderItems().forEach(item -> item.setOrder(order));

        repository.save(order);

        inventoryClient.updateStock(orderRequest.getOrderItems());

        return mapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

}
