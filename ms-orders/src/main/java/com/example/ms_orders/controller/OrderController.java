package com.example.ms_orders.controller;

import com.example.ms_orders.dto.OrderRequest;
import com.example.ms_orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest request){
        service.createOrder(request);
        return "Order placed successfully";
    }

}
