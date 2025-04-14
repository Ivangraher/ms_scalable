package com.example.ms_orders.events;

import com.example.ms_orders.model.OrderStatus;

public record OrderEvent(String orderNumber, int items, OrderStatus status) {
}
