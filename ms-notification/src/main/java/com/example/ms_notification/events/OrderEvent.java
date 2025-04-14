package com.example.ms_notification.events;

import com.example.ms_notification.model.OrderStatus;

public record OrderEvent(String orderNumber, int items, OrderStatus status) {
}
