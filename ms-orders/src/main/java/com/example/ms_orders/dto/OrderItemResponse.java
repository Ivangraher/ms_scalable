package com.example.ms_orders.dto;

public record OrderItemResponse(
        Long Id,
        String sku,
        Double price,
        Integer quantity
){}
