package com.example.ms_orders.mapper;


import com.example.ms_orders.dto.OrderItemRequest;
import com.example.ms_orders.dto.OrderItemResponse;
import com.example.ms_orders.dto.OrderResponse;
import com.example.ms_orders.model.Order;
import com.example.ms_orders.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) throw new IllegalArgumentException("Order no puede ser nulo");

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderItems().stream()
                        .map(this::toOrderItemsResponse)
                        .collect(Collectors.toList())
        );
    }

    public OrderItemResponse toOrderItemsResponse(OrderItem orderItem) {
        if (orderItem == null) throw new IllegalArgumentException("OrderItem no puede ser nulo");

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getSku(),
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
    }

    public OrderItem toEntity(OrderItemRequest request, Order order) {
        if (request == null) throw new IllegalArgumentException("OrderItemRequest no puede ser nulo");

        return OrderItem.builder()
                .id(request.getId())
                .sku(request.getSku())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .order(order)
                .build();
    }
}

