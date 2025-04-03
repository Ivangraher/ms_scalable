package com.example.ms_products.mapper;

import com.example.ms_products.dto.ProductRequest;
import com.example.ms_products.dto.ProductResponse;
import com.example.ms_products.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ProductRequest no puede ser nulo");
        }

        return Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product no puede ser nulo");
        }

        return ProductResponse.builder()
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}

