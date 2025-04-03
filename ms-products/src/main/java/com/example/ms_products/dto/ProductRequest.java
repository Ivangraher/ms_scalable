package com.example.ms_products.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private String sku;
    private String description;
    private Double price;
    private Boolean status;
}
